package ge.quickscope.buisness;

/**
 * Created by Jay on 7/5/2015.
 */

import ge.quickscope.model.items.ConfigResponse;

/**
 * Configuration parameters for MovieDatabase api.
 */
public interface MovieDBConfigurationLogic extends Logic {

    /**
     * Request configuration data
     */
    void requestConfigurationData();

    /**
     * Callback used when configuration data is received
     */
    void onConfigurationDataReceive(ConfigResponse configResponse);

    /**
     * Endpoint configuration to get images from MovieDB
     */
    void configureImageUrl(ConfigResponse configResponse);

    /**
     * Sends configured url to Presenter to get images from MovieDB
     *
     * @param url
     */
    void sendConfiguredUrlToPresenter(String url);
}
