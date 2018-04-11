package intsys.search;

import java.util.LinkedHashSet;
import java.util.Set;

import aima.core.agent.Action;
import aima.core.search.framework.ActionsFunction;
import aima.core.search.framework.GoalTest;
import aima.core.search.framework.HeuristicFunction;
import aima.core.search.framework.ResultFunction;
import aima.core.search.framework.StepCostFunction;
import aima.core.util.datastructure.XYLocation;

/**
 * Provides useful functions for the route problem.
 * 
 * @author Enrique and Ezequiel
 *
 */
public class RouteFunctionFactory {

	private static ActionsFunction _actionsFunction = null;
	private static ResultFunction _resultFunction = null;
	private static GoalTest _goalTest = null;
	private static StepCostFunction _stepCostFunction = null;
	private static HeuristicFunction _heuristicFunction = null;
	
	public static ActionsFunction getActionsFunction() {
		if(_actionsFunction == null) 
			_actionsFunction = new RouteActionsFunc(); 
		return _actionsFunction;
	}
	
	public static ResultFunction getResultFunction() {
		if(_resultFunction == null)
			_resultFunction = new RouteResultFunc();
		return _resultFunction;
	}
	
	public static GoalTest getGoalTest() {
		if(_goalTest == null)
			_goalTest = new RouteGoalTest();
		return _goalTest;
	}
	
	public static StepCostFunction getStepCostFunction () {
		if(_stepCostFunction == null)
			_stepCostFunction = new RouteStepCostFunc();
		return _stepCostFunction;
		
	}
	
	public static HeuristicFunction getHeuristicFunction () {
		if(_heuristicFunction == null)
			_heuristicFunction = new RouteHeuristicFunc();
		return _heuristicFunction;
		
	}
	
	/** Computes the cost of executing action a to go from state s to state sDelta. */
	private static class RouteStepCostFunc implements StepCostFunction {
		@Override
		public double c(Object s, Action a, Object sDelta){
			RouteBoard board= (RouteBoard) s;
			RouteAction action=(RouteAction) a;

			return (double)(board.getInteger(action.getX(), action.getY()));
		
		}
		
	}
	
	/** Heuristic function to guide the informed search methods. */
	private static class RouteHeuristicFunc implements HeuristicFunction {
		@Override
		public double h(Object state){
			RouteBoard board= (RouteBoard) state;
			
			// YOU MUST PROVIDE A HEURISTIC FUNCTION HERE			
			return Math.abs(board.getCurrentX()-board.getGoalX())+Math.abs(board.getCurrentY()-board.getGoalY());
		}
		
	}
	
	
	/** Provides actions for every state */
	private static class RouteActionsFunc implements ActionsFunction {
		


		@Override
		public Set<Action> actions(Object s) {
			RouteBoard board = (RouteBoard) s;
			Set<Action> actions = new LinkedHashSet<Action>();
			int x=board.getCurrentX();
			int y=board.getCurrentY();
			
			/* Go left */
			if (board.isValidNonEmpty(x,y-1))
			{
				XYLocation pos = new XYLocation(x,y-1);				
				actions.add(new RouteAction(RouteAction.LEFT,
						pos));	
			}

			/* Go up */
			if (board.isValidNonEmpty(x-1,y))
			{
				XYLocation pos = new XYLocation(x-1,y);				
				actions.add(new RouteAction(RouteAction.UP,
						pos));	
			}
			
			/* Go right */
			if (board.isValidNonEmpty(x,y+1))
			{
				XYLocation pos = new XYLocation(x,y+1);				
				actions.add(new RouteAction(RouteAction.RIGHT,
						pos));	
			}

			/* Go down */
			if (board.isValidNonEmpty(x+1,y))
			{
				XYLocation pos = new XYLocation(x+1,y);				
				actions.add(new RouteAction(RouteAction.DOWN,
						pos));
			}

			return actions;
		}
		
	}

	/** Computes the result of performing an action on a state. */
	private static class RouteResultFunc implements ResultFunction {

		@Override
		public Object result(Object s, Action a) {
			if(a instanceof RouteAction) {
				RouteAction sa = (RouteAction) a;
				RouteBoard board = ((RouteBoard)s).clone();
				board.setCurrentPos(sa.getX(), sa.getY());

				s = board;
			}
			return s;
		}
		
	}
	
	private static class RouteGoalTest implements GoalTest {

		@Override
		public boolean isGoalState(Object state) {
			RouteBoard board = (RouteBoard) state;
			
			return ((board.getGoalX()==board.getCurrentX()) &&
					(board.getGoalY()==board.getCurrentY()));
			
		}
		
	}
}
