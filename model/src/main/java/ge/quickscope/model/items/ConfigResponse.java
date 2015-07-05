package ge.quickscope.model.items;

/**
 * Created by Jay on 7/5/2015.
 */
public class ConfigResponse {
    private ConfigImages images;
    private String [] change_keys;

    public ConfigImages getImages(){
        return images;
    }

    public class ConfigImages {
        private String base_url;
        private String secure_base_url;
        private String [] backdrop_sizes;
        private String [] logo_sizes;
        private String [] poster_sizes;
        private String [] profile_sizes;
        private String [] still_sizes;

        public String getBase_url() {

            return base_url;
        }

        public String getSecure_base_url() {

            return secure_base_url;
        }

        public String[] getBackdrop_sizes() {

            return backdrop_sizes;
        }

        public String[] getLogo_sizes() {

            return logo_sizes;
        }

        public String[] getPoster_sizes() {

            return poster_sizes;
        }

        public String[] getProfile_sizes() {

            return profile_sizes;
        }

        public String[] getStill_sizes() {

            return still_sizes;
        }
    }
}
