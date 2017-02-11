package myapp.findyouritem;

/**
 * Created by XIAO on 2017/2/5.
 */
public class Items {
    int id;
    String brand;
    String discription;
    int price;
    String type;

    public Items(){}

    public Items(int id, String brand, String discription,
                 int price, String type){
        this.id = id;
        this.brand = brand;
        this.discription = discription;
        this.price = price;
        this.type = type;
    }

    public Items(String brand, String discription,
                 int price, String type){
        super();
        this.brand = brand;
        this.discription = discription;
        this.price = price;
        this.type = type;
    }

    public int getID(){
        return this.id;
    }
    public void setID(int id){
        this.id = id;
    }

    public String getBrand(){
        return this.brand;
    }
    public void setBrand(String brand){
        this.brand = brand;
    }

    public String getDisc(){
        return this.discription;
    }
    public void setDisc(String dis){
        this.discription = dis;
    }

    public int getPrice(){
        return this.price;
    }
    public void setPrice(int price){
        this.price = price;
    }

    public String getType(){
        return this.type;
    }
    public void setType(String type){
        this.type = type;
    }

}
