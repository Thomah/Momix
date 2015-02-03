package fr.eseo.momix;

/**
 * Created by etudiant on 03/02/2015.
 */
    public class Modele{

        private int icon;
        private String title;
        private String counter;
        private boolean isGroupHeader = false;

        public Modele(String title) {
            super();
            this.title = title;
        }

    public String getCounter() {
        return counter;
    }

    public int getIcon() {
        return icon;
    }

    public String getTitle() {
        return title;
    }


    public boolean isGroupHeader() {
        return isGroupHeader;
    }

    public void setGroupHeader(boolean isGroupHeader) {
        this.isGroupHeader = isGroupHeader;
    }
}

