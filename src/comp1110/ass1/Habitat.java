package comp1110.ass1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

/**
 * This class represents a habitat, a four-by-four grid of places.
 *
 * The class and the those that it refer to provide the core logic of
 * the game, which is used by the GUI, which runs the game in a window.
 */
public class Habitat {
    public static final int SIDE = 4;            // dimensions of board
    public static final int PLACES = SIDE*SIDE;  // number of places

    private Place[] places;                       // the list of places, represented as a single array

    private String lastPlacement = "";                        // used to remember (cache) the last placement made
    private int[] placementCoverage = new int[PLACES];        // array that contains a count of number of pieces covering each square on the habitat (0 means the square is not covered, 1 means one piece covers it, numbers higher than one mean overlapping pieces cover it)
    private int coverageCount;                                // the total number of squares covered
    private boolean[] placementErrors = new boolean[PLACES];  // array that contains a boolean indicating for each square whether or not there was an error
    private int errorCount;                                   // the total number of errors

    private String solution;                      // the solution to the current game



    /**
     * Constructor for a habitat, given a level of difficulty for the new habitat
     *
     * This shold create a new habitat with a single valid solution and a level of
     * difficulty that corresponds to the argument difficulty.
     *
     * @param difficulty A value between 0 (easiest) and 10 (hardest) specifying the desired level of difficulty.
     */
    public Habitat(double difficulty) {
        // FIXME Task 9: replace this code with code that generates new habitats
        coverageCount=0;
        errorCount=0;
        switch ((int) difficulty) {
            case 0:
                this.places=new Habitat("LLWWPWPWWPWPLLPL").getPlaces();
                break;
            case 1:
                this.places=new Habitat("LWWLPWPWWPWPLLPL").getPlaces();
                break;
            case 2:
                this.places=new Habitat("LWWLPWPWWPWPLLPL").getPlaces();
                break;
            case 3:
                this.places=new Habitat("WLLWWPWPPWPWLPLL").getPlaces();
                break;
            case 4:
                this.places=new Habitat("WLLWWPWPPWPWLPLL").getPlaces();
                break;
            case 5:
                this.places=new Habitat("LWLWLWLWLWLWLWWP").getPlaces();
                break;
            case 6:
                this.places=new Habitat("LWLWLWLWLWLWLWWP").getPlaces();
                break;
            case 7:
                this.places=new Habitat("PWLWLWLWLWLWLWWP").getPlaces();
                break;
            case 8:
                this.places=new Habitat("PWLWLWLWLWLWLWWP").getPlaces();
                break;
            case 9:
                this.places=new Habitat("LWWWLLLLWWWWWWLW").getPlaces();
                break;
            case 10:
                this.places=new Habitat("LWLLLWLPWLWPLWWL").getPlaces();
                break;
        }
    }
    public Place[] getPlaces(){
        return places;
    }


    /**
     * Constructor for a habitat, given a string describing the habitat
     *
     * @param habitat A 16-character string describing a habitat in terms of the 16 places that make it up
     */
    Habitat (String habitat) {
        this(placesFromString(habitat));
    }


    /**
     * Constructor for a habitat, given an array of places
     *
     * @param places A 16 element array of places.
     */
    Habitat (Place[] places) {
        this.places = places;
        if (places == null || !isHabitatLegal()) throw new IllegalArgumentException("Bad habitat");
    }


    /**
     * Inspect the placementErrors field and count the number of grid squares that currently have errors
     * and return that number.
     *
     * @return The number of grid squares that currently have errors.
     */
    int getErrorCount() {
        int error= 0;
        for (boolean i: placementErrors) {
            if (i)
                error++;
        }
        return error;
    }


    /**
     * Inspect the placementCoverage field and count the number of grid squares that are currently covered
     * by one or more pieces and return that number.
     *
     * @return The number of grid squares that are currently covered by at least one piece, according to the placementCoverage field.
     */
    int getCoverageCount() {
        coverageCount = 0;
        for (int i:placementCoverage) {
            if(i > 0)
                coverageCount++;
        }
        return coverageCount;
    }


    /**
     * Find all solutions to this habitat, and return them as an array of strings, each string
     * describing a placement of the pieces, according to the way placements are encoded.
     *
     * If the habitat was a sound one, there should be only one solution, so the array will be
     * of length one.   Therefore, this method can also be used to check whether a habitat is
     * a sound one.   If it returns an array of length one, then the habitat is a good one.
     *
     * @return An array of strings representing the set of all solutions to this habitat
     */
    public String[] getSolutions() {
        ArrayList<String>solutions=new ArrayList<>();
        ArrayList<String>Piece_Q=validPiecePlacement(0);
        ArrayList<String>Piece_R=validPiecePlacement(1);
        ArrayList<String>Piece_S=validPiecePlacement(2);
        ArrayList<String>Piece_T=validPiecePlacement(3);
        ArrayList<String>Piece_U=validPiecePlacement(4);
        ArrayList<String>Piece_V=validPiecePlacement(5);

        ArrayList<ArrayList>Pieces = new ArrayList<>();
        Pieces.add(Piece_Q);
        Pieces.add(Piece_R);
        Pieces.add(Piece_S);
        Pieces.add(Piece_T);
        Pieces.add(Piece_U);
        Pieces.add(Piece_V);

//        Collections.sort(Pieces, new Comparator<ArrayList>() {
//            @Override
//            public int compare(ArrayList o1, ArrayList o2) {
//                return o1.size()-o2.size();
//            }
//        });

        Collections.sort(Pieces, (o1, o2) -> o1.size()-o2.size());

        Queue Q = new Queue();
        Queue R = new Queue();
        Queue S = new Queue();
        Queue T = new Queue();
        Queue U = new Queue();

        Q.queue = Pieces.get(0);
        while (!Q.isempty()) {
            String Q_current = Q.dequeue();
            for (int i = 0; i < Pieces.get(1).size(); i++) {
                if (isPlacementSound(Q_current+Pieces.get(1).get(i)))
                    R.enqueue(Q_current+Pieces.get(1).get(i));
            }
        }
        while (!R.isempty()) {
            String R_current = R.dequeue();
            for (int i = 0; i < Pieces.get(2).size(); i++) {
                if (isPlacementSound(R_current+Pieces.get(2).get(i)))
                    S.enqueue(R_current+Pieces.get(2).get(i));
            }
        }
        while (!S.isempty()) {
            String S_current = S.dequeue();
            for (int i = 0; i < Pieces.get(3).size(); i++) {
                if (isPlacementSound(S_current+Pieces.get(3).get(i)))
                    T.enqueue(S_current+Pieces.get(3).get(i));
            }
        }
        while (!T.isempty()) {
            String T_current = T.dequeue();
            for (int i = 0; i < Pieces.get(4).size(); i++) {
                if (isPlacementSound(T_current+Pieces.get(4).get(i)))
                    U.enqueue(T_current+Pieces.get(4).get(i));
            }
        }
        while (!U.isempty()) {
            String U_current = U.dequeue();
            for (int i = 0; i < Pieces.get(5).size(); i++) {
                if (isPlacementSound(U_current+Pieces.get(5).get(i)))
                    solutions.add(U_current+Pieces.get(5).get(i));
            }
        }
        String[]sol=new String[solutions.size()];
        for (int i = 0; i < solutions.size(); i++) {
            sol[i]=solutions.get(i);
        }
        return sol;
    }
    public  ArrayList<String> validPiecePlacement(int piece_num) {
        ArrayList<String> output = new ArrayList<>();
        char piece = (char) ('Q' + piece_num);
        if (piece >='Q' && piece <= 'T') {
            for (int i = 0; i < 16; i++) {
                char position = (char) ('A' + i);
                for (int j = 0; j < 4; j++) {
                    char orentation = (char) ('W' + j);
                    String placement = "" + position + piece + orentation;
                    switch (orentation) {
                        case ('W'):
                            if (i % 4 == 3 || i >= 12)
                                placement = "";
                            break;
                        case ('X'):
                            if (i % 4 == 0 || i >= 12)
                                placement = "";
                            break;
                        case ('Y'):
                            if (i % 4 == 0 || i <= 3)
                                placement = "";
                            break;
                        case ('Z'):
                            if (i % 4 == 3 || i <= 3)
                                placement = "";
                            break;
                    }
                    if (placement == "")
                        continue;
                    if (isPlacementSound(placement))
                        output.add(placement);
                }
            }
        }
        if (piece == 'U') {
            for (int i = 0; i < 16; i++) {
                char position = (char) ('A' + i);
                for (int j = 0; j < 2; j++) {
                    char orentation = (char) ('W' + j);
                    String placement = "" + position + piece + orentation;
                    switch (orentation) {
                        case ('W'):
                            if (i % 4 == 3)
                                placement = "";
                            break;
                        case ('X'):
                            if (i >= 12)
                                placement = "";
                            break;
                    }
                    if (placement == "")
                        continue;
                    if (isPlacementSound(placement))
                        //                    System.out.println(placement);
                        output.add(placement);
                }
            }
        }
        if (piece == 'V') {
            for (int i = 0; i < 16; i++) {
                char position = (char) ('A' + i);
                for (int j = 0; j < 4; j++) {
                    char orentation = (char) ('W' + j);
                    String placement = "" + position + piece + orentation;
                    switch (orentation) {
                        case ('W'):
                            if (i % 4 == 3)
                                placement = "";
                            break;
                        case ('X'):
                            if (i >= 12)
                                placement = "";
                            break;
                        case ('Y'):
                            if (i % 4 == 0)
                                placement = "";
                            break;
                        case ('Z'):
                            if (i <= 3)
                                placement = "";
                            break;
                    }
                    if (placement == "")
                        continue;
                    if (isPlacementSound(placement))
                        output.add(placement);
                }
            }
        }

        return output;
    }

    /**
     * Return the solution to the game.  The solution is calculated lazily, so first
     * check whether it's been calculated.
     *
     * @return A string representing the solution to this habitat.
     */
    public String getSolution() {
        if (solution == null) setSolution();
        return solution;
    }


    /**
     * Establish the solution to this game.
     */
    private void setSolution() {
        String[] solutions = getSolutions();
        if (solutions.length != 1) {
            String h = "";
            for (Place p : places) h += p;
            throw new IllegalArgumentException("Habitat "+h+(solutions.length == 0 ? " has no " : " has more than one ")+"solution");
        } else
            solution = solutions[0];
    }


    /*
     * Determine whether a habitat is legal.  It must:
     * - be the correct length (16)
     * - have enough land to house all animals (5)
     * - have enough water to house all fish (6)
     */
    boolean isHabitatLegal() {
        if (places.length != PLACES) return false;

        int land = 0;
        int water = 0;
        for (Place p : places) {
            if (p == Place.L) land++;
            else if (p == Place.W) water++;
        }
        return (land >= 5 || water >= 6);
    }


    /**
     * Decode a string representing a habitat into an array of places
     * @param habitat A string representing a habitat
     * @return An array of places
     */
    private static Place[] placesFromString(String habitat) {
        if (habitat == null) return null;
        if (habitat.length() != PLACES) return null;
        Place [] p = new Place[PLACES];
        for (int i = 0; i < PLACES; i++) {
            p[i] = Place.valueOf(habitat.substring(i,i+1));
            if (p[i] == null) {
                throw new IllegalArgumentException("Bad places string: " + habitat);
            }
        }
        return p;
    }


    /**
     * Determine whether a place is hostile to a creature
     * @param place The place in question
     * @param creature The creature in question
     * @return False if the place is off the board (-1) or it is not hostile to the creature
     */
    boolean isHostile(int place, Creature creature) {
        return place >= 0 && places[place].hostileTo(creature);
    }


    /**
     * Setter method for the placementErrors field
     * @param errors The array of errors
     */
    void setPlacementErrors(boolean [] errors) { placementErrors = errors; }


    /**
     * Get the placement errors given a placement string.  The value
     * is only recalculated if the placement string differs from the last
     * one.
     *
     * @param placement The string representing the placement to be checked.
     * @return An array of booleans representing any placement errors
     */
    public boolean[] getPlacementErrors(String placement) {
        if (!placement.equals(lastPlacement))
            renewPlacement(placement);

        return placementErrors;
    }


    /**
     * Determine whether a placement is sound.  This is only true if the placement
     * has no errors.   The error count variable is only recalculated if the placement
     * string differs from the last one.
     *
     * @param placement The string representing the placement to be checked.
     * @return true if the placement contains no errors.
     */
    public boolean isPlacementSound(String placement) {
        if (!placement.equals(lastPlacement))
            renewPlacement(placement);
        boolean output = true;
        for (int i:getPlacementCoverage(placement)) {
            if (i>1)
                output=false;
        }
        return errorCount == 0 && output;
    }


    /**
     * Setter method for the placementCoverage field
     * @param coverage The array of errors
     */
    void setPlacementCoverage(int [] coverage) {
        placementCoverage = coverage;
    }


    /**
     * Return an array representing the coverage of a given placement.   For each
     * square on the board, an integer reflects the number of pieces covering the
     * square. The value is only recalculated if the placement string differs from
     * the last one.
     *
     * @param placement The string representing the placement to be checked.
     * @return  An array of integers representing the coverage of the placement.
     */
    public int[] getPlacementCoverage(String placement) {
        if (!placement.equals(lastPlacement))
            renewPlacement(placement);

        return placementCoverage;
    }


    /**
     * Determine whether a placement completely covers the habitat.  This is only true
     * if the placement coverage is 1 at every point on the habitat.  The coverage count
     * variable is only recalculated if the placement string differs from the last one.
     *
     * @param placement The string representing the placement to be checked.
     * @return true if the placement covers the entire habitat
     */
    public boolean isPlacementComplete(String placement) {
        if (!placement.equals(lastPlacement))
            renewPlacement(placement);

        return coverageCount == PLACES;
    }


    /**
     * A new placement has been made, update coverage and error counts accordingly
     *
     * @param placement A string representing a new placement.
     */
    private void renewPlacement(String placement) {
        PiecePlacement[] placements = PiecePlacement.getPlacements(placement);
        Arrays.fill(placementCoverage, 0);
        Arrays.fill(placementErrors, Boolean.FALSE);
        for (PiecePlacement pp : placements) {
            pp.updateCoverageAndErrors(this, placementCoverage, placementErrors);
        }
        lastPlacement = placement;
        coverageCount =getCoverageCount();
        errorCount = getErrorCount();
    }


    /**
     * Encode this habitat as a string
     *
     * @return A string representing the sixteen places in this habitat
     */
    @Override
    public String toString() {
        String rtn = "";
        for (int i = 0; i < PLACES; i++) {
            rtn += places[i];
        }
        return rtn;
    }

    class Queue{
        ArrayList<String> queue;
        Queue(){
            queue= new ArrayList<>();
        }
        public void enqueue(String a){
            queue.add(a);
        }
        public String dequeue(){
            String output= queue.get(0);
            queue.remove(output);
            return output;
        }
        public boolean isempty(){
            return queue.size()==0;
        }
        @Override
        public String toString(){
            String output="";
            for (int i = 0; i < queue.size(); i++) {
                output+=(output!="" ? ","+queue.get(i) :queue.get(i));
            }
            return output;
        }
    }
}
