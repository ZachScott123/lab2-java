public static void main(String[] args) {
    LobsterStream book = new LobsterStream();
    
    book.submit(1, 10000, 100);
    book.submit(1, 9900, 50);
    book.submit(-1, 10100, 75);
    System.out.println("Before execution: " + book.byId.size() + " orders");
    
    book.execute(1, 120);
    
    System.out.println("After execution: " + book.byId.size() + " orders");
    System.out.println("Bids: " + book.bids.size() + " price levels");
    System.out.println("Asks: " + book.asks.size() + " price levels");
}

//temporary main for testing the LobsterStream class (TODO 1).