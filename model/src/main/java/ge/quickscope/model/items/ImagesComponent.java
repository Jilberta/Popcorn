package ge.quickscope.model.items;

import java.util.List;

/**
 * Created by Jay on 7/5/2015.
 */
public class ImagesComponent {
    private int id;
    private List<MovieImage> backdrops;

    public int getId() {
        return id;
    }

    public List<MovieImage> getBackdrops() {
        return backdrops;
    }

    public class MovieImage {
        private int width, height, vote_count;
//        private int height;
        private String file_path, iso_639_1;
//        private String iso_639_1;
        private float aspect_ratio, vote_average;
//        private float vote_average;
//        private int vote_count;

        public String getFile_path() {
            return file_path;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }

        public String getIso_639_1() {
            return iso_639_1;
        }

        public float getAspect_ratio() {
            return aspect_ratio;
        }

        public float getVote_average() {
            return vote_average;
        }

        public int getVote_count() {
            return vote_count;
        }
    }
}
