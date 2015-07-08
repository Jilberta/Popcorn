package ge.quickscope.buisness;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

import ge.quickscope.model.DataSource;
import ge.quickscope.model.items.ConfigResponse;

/**
 * Created by Jay on 7/8/2015.
 */
public class MovieDBConfigurationLogicController implements MovieDBConfigurationLogic{

    private final String NEEDED_QUALITY    = "w780";
    private final String ORIGINAL_QUALITY   = "original";
    private final DataSource dataSource;
    private final Bus mainBus;

    @Inject
    public MovieDBConfigurationLogicController(DataSource dataSource, Bus bus){
        this.dataSource = dataSource;
        this.mainBus = bus;
        this.mainBus.register(this);
    }

    @Override
    public void requestConfigurationData() {
        dataSource.getConfiguration();
    }

    @Subscribe
    @Override
    public void onConfigurationDataReceive(ConfigResponse configResponse) {
        mainBus.unregister(this);
        configureImageUrl(configResponse);
    }

    @Override
    public void configureImageUrl(ConfigResponse configResponse) {
        String url;
        if(configResponse.getImages() != null){
            String quality = "";
            url = configResponse.getImages().getBase_url();
            for(int i = 0; i < configResponse.getImages().getBackdrop_sizes().length; i++){
                String q = configResponse.getImages().getBackdrop_sizes()[i];
                if (NEEDED_QUALITY.equals(q)) {
                    quality = NEEDED_QUALITY;
                    break;
                }
            }

            if(quality.equals(""))
                quality = ORIGINAL_QUALITY;

            url += quality;
            sendConfiguredUrlToPresenter(url);
        }
    }

    @Override
    public void sendConfiguredUrlToPresenter(String url) {
        mainBus.post(url);
    }

    @Override
    public void execute() {
        requestConfigurationData();
    }
}
