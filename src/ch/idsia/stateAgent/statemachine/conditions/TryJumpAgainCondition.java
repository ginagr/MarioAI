package ch.idsia.stateAgent.statemachine.conditions;

import ch.idsia.mario.environments.Environment;
import ch.idsia.stateAgent.helpers.Helper;
import ch.idsia.stateAgent.statemachine.ICondition;

public class TryJumpAgainCondition implements ICondition {

    public boolean test(Environment observation){

        Helper helper = new Helper(observation);

        return !helper.getIsMarioStuck(); // the new helper goes here
    }
}