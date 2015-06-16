package com.spm.cafe529.Struct;

/**
 * Created by sungho2 on 2015-05-29.
 */
public class WishItem {
    String itemName;
    int itemCount;

    public WishItem (String itemName, int itemCount) {
        this.itemName = itemName;
        this.itemCount = itemCount;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public boolean calcItem(int type) {

        try {
            if (type == 1) { //++
                if(itemCount < 99){
                    itemCount++;
                    return true;
                }

            } else if (type == 2) { //--
                if(itemCount > 0){
                    itemCount--;
                    return true;
                }
            }

            return false;

        } catch(Exception e) {
            return false;
        }
    }
}
