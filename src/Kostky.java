import java.util.Random;

public class Kostky {
    Random rn = new Random();
    int NUMBER_OF_PLAYERS = 2;//počet hráčů
    int numberOfDie = 6;//počet kostek
    int numberToExclude = 0;//číslo, které se smaže kvůli trojicím atd.
    int tempPoints = 0;//new int[NUMBER_OF_PLAYERS];
    int[] conto = new int[NUMBER_OF_PLAYERS];//založí číselné pole velké podle počtu hráčů, do kterého se bude ukládat počet bodů, co hráči vyhráli
    int player = 1;//proměnná určující momentálního hráče

    public int getNumberOfDie() {return numberOfDie;}
    public void setNumberOfDie(int numberOfDie) {this.numberOfDie = numberOfDie;}
    public int getNumberToExclude() {
        return numberToExclude;
    }
    public void setNumberToExclude(int numberToExclude) {
        this.numberToExclude = numberToExclude;
    }

    public Kostky() {//konstruktor hráče
        throwDice(player);//hodí kostkami
        System.out.println("Momentální body: "+tempPoints+"\nHázet dál s "+getNumberOfDie());

    }

    public static void main(String[] args) {//main
        new Kostky();
    }//main

    public int[] throwDice(int player){//hození kostek
        if(getNumberOfDie()<1){//pokud už nejsou kostky, vezmi znovu šest kostek
            setNumberOfDie(6);
        }
        int[] hand = new int[getNumberOfDie()];//vytvoří pole hand[], které představuje kostky

        for (int i = 0;i<getNumberOfDie();i++){//naplní pole hand[] náhodnými hody od 1-6
            hand[i] = rn.nextInt(1,6);
            System.out.println(hand[i]);
        }

        if(lineOfNumbers(hand)==true){//zkontroluje jestli nepadla postupka
            tempPoints += 2000;//pokud ano, přičte 2000 bodů
            for(int k = 0;k<hand.length;k++) hand[k] = 0;//a smaže hodnoty na kostkách, aby se nemohli započítat dvakrát
        }
        tempPoints += multiples(hand);//zavolá metodu multiples(), kde zkontroluje pro trojice atd.
        for (int j = 0; j < hand.length; j++) {//všechny čísla obsažené v trojicích atd. se smažou
            if (hand[j] == getNumberToExclude()) {
                hand[j] = 0;
            }
        }
        tempPoints += multiples(hand);//zopakuje znovu, kdyby byli dvě trojice
        for (int j = 0; j < hand.length; j++) {
            if (hand[j] == getNumberToExclude()) {
                hand[j] = 0;
            }
        }
        for (int i = 0;i<numberOfDie;i++){
            if(hand[i]==1){
                tempPoints += 100;
                setNumberOfDie(getNumberOfDie()-1);
            } else if (hand[i]==5) {
                tempPoints += 50;
                setNumberOfDie(getNumberOfDie()-1);
            }
        }
        return hand;
    }

    public int multiples(int[] hand){//zkontroluje trojice atd.
        int tempPointsMultiples = 0;
        int numberToCompare = 0;//číslo co se bude porovnávat
        for (int i=1;i<6;i++){//zkontroluje pokud se nějaké číslo nevyskytuje vícekrát
            numberToCompare = i;
            int count = 0;//počet nalezených čísel
            for (int numberOnDice : hand) {
                if (numberOnDice == numberToCompare) {
                    count++;
                }
            }
            if(numberToCompare==1) numberToCompare = 10;//kvůli 1000 se změní 1 na 10, kvůli jednoduššímu násobení
            if(count>=3){
                tempPointsMultiples = (int) (numberToCompare * 100 * Math.pow(2,count-3));
                if(numberToCompare==10) numberToCompare = 1;
                setNumberToExclude(numberToCompare);
            }
        }

        return tempPointsMultiples;
    }

    public boolean lineOfNumbers(int[] hand){
        boolean hasAllNumbers = true;

        for (int i = 1; i <= 6; i++) {
            boolean found = false;
            for (int j = 0; j < hand.length; j++) {
                if (hand[j] == i) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                hasAllNumbers = false;
                break;
            }
        }
        return hasAllNumbers;
    }
}