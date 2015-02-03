package fr.eseo.momix;

import static fr.eseo.momix.R.id.item_icon;

/**
 * Created by etudiant on 03/02/2015.
 */
    public class Modele{

        private int icon;
        private String title;

        public Modele(String title) {
            super();
            this.icon = R.drawable.ic_action_remove;
            this.title = title;
        }


    public int getIcon() {
        return icon;
    }

    public String getTitle() {
        return title;
    }


}

