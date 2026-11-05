
public class Player {

    String PlayerColor = "white";

    public void changePlayerColor(){
        this.PlayerColor = this.PlayerColor == "white" ? "black":"white";
    }

    public Player(String color){

        this.PlayerColor = color;
    }
}
