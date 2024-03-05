package deal;

public enum DealName {
    BUY_ONE_GET_ONE_HALF_PRICE("buy 1 get 1 half price"),
    TWO_FOR_THREE_DEAL("two for three");

    public final String dealName;

    DealName(String userCommand) {
        this.dealName = userCommand;
    }

}
