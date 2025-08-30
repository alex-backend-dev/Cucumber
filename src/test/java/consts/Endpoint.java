package consts;

public enum Endpoint {

    GET_ALL_BOARDS("/members/{id}/boards"),
    GET_A_BOARD("/boards/{id}"),
    GET_A_CARD("/cards/{id}"),
    CREATE_A_BOARD("/boards/"),
    CREATE_A_CARD("/cards"),
    UPDATE_A_BOARD("/boards/{id}"),
    UPDATE_A_CARD("/cards/{id}"),
    DELETE_A_BOARD("/boards/{id}"),
    DELETE_A_CARD("/cards/{id}");

    private final String url;

    Endpoint(String url) {
        this.url = url;
    }

    public String getUrl(){
        return url;
    }
}
