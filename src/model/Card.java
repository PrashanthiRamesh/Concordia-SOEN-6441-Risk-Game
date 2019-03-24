package model;

public class Card  {

    private String name;

    private int typeNumber;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTypeNumber() {
        return typeNumber;
    }

    public void setTypeNumber(int typeNumber) {
        this.typeNumber = typeNumber;
    }

    public String getNameByTypeNumber(int typeNumber){
        String name="";
        switch (typeNumber){
            case 1:
                name="Infantry";
                break;
            case 2:
                name="Cavalry";
                break;
            case 3:
                name="Cannon";
                break;
            default:
                break;
        }
        return name;
    }
}
