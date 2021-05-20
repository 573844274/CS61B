package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

import java.util.Comparator;
import java.util.LinkedList;

public class Solver {
    private WorldState initialWorldState;
    private MinPQ<SearchNode> fringe;
    private SearchNode solution;
    //private HashSet<WorldState> checkedState; // this will cause some problems

    private class SearchNode {
        private WorldState worldState;
        private int moves;
        private SearchNode previous;
        private int estimatedDistance;

        SearchNode(WorldState worldState, int moves, SearchNode previous) {
            this.worldState = worldState;
            this.moves = moves;
            this.previous = previous;
            this.estimatedDistance = worldState.estimatedDistanceToGoal();
        }
    }

    private class SearchNodeComparator implements Comparator<SearchNode> {
        @Override
        public int compare(SearchNode sn1, SearchNode sn2) {
            int cost1 = sn1.moves + sn1.estimatedDistance;
            int cost2 = sn2.moves + sn2.estimatedDistance;
            return cost1 - cost2;
        }
    }
    /** Constructor which solves the puzzle, computing
     everything necessary for moves() and solution() to
     not have to solve the problem again. Solves the
     puzzle using the A* algorithm. Assumes a solution exists.
     */
    public Solver(WorldState initial) {
        SearchNodeComparator snc = new SearchNodeComparator();
        fringe = new MinPQ<SearchNode>(snc);
        //checkedState = new HashSet<WorldState>();
        initialWorldState = initial;
        SearchNode initialNode = new SearchNode(initialWorldState, 0, null);
        fringe.insert(initialNode);
        this.solution = process();
    }

    /** Returns the minimum number of moves to solve the puzzle starting
     at the initial WorldState.
     */
    public int moves() {
        return solution.moves;
    }

    /** Returns a sequence of WorldStates from the initial WorldState
     to the solution.
     */
    public Iterable<WorldState> solution() {
        LinkedList<WorldState> sequenceOfSolution = new LinkedList<WorldState>();
        Stack<WorldState> worldStatesStack = new Stack<WorldState>();
        SearchNode nodePointer = solution;
        while (nodePointer.previous != null) {
            worldStatesStack.push(nodePointer.worldState);
            nodePointer = nodePointer.previous;
        }
        while (!worldStatesStack.isEmpty()) {
            sequenceOfSolution.add(worldStatesStack.pop());
        }
        return sequenceOfSolution;
    }

    private SearchNode nextStep() {
        SearchNode currentNode = fringe.delMin();
        if (solved(currentNode)) {
            return currentNode;
        }
            for (WorldState ws : currentNode.worldState.neighbors()) {
                if (currentNode.previous == null
                        || !currentNode.previous.worldState.equals(ws)) {
                    fringe.insert(new SearchNode(ws,
                            currentNode.moves + 1, currentNode));
                }
            }
            return currentNode;
    }

    /**
     * The whole process to find the solution.
     */
    private SearchNode process() {
        SearchNode currentAnswer = nextStep();
        while (!solved(currentAnswer)) {
            currentAnswer = nextStep();
        }
        return currentAnswer;
    }

    private boolean solved(SearchNode currentNode) {
        return currentNode.worldState.isGoal();
    }
}
