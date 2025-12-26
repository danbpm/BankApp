public enum TransactionType {
    DEPOSIT("ПОПОЛНЕНИЕ"),
    WITHDRAW("СНЯТИЕ"),
    TRANSFER("ПЕРЕВОД");

    private final String type;

    TransactionType(String type){
        this.type = type;
    }

    @Override
    public String toString(){
        return type;
    }
}
