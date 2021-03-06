package ch.idsia.stateAgent.helpers;

import ch.idsia.mario.environments.Environment;
import java.util.HashMap;
import java.util.Map;

/**
 * Helper class that checks the environment and returns booleans for FSM
 */
public class Helper {

    private Environment env;

    public Helper(Environment env){
        this.env = env;
    }

    /**
     * @return number of enemies in current scene
     */
    public int getNumberOfEnemies() {
        int count = 0;
        byte[][] levelScene = env.getEnemiesObservation();
        for (byte[] aLevelScene : levelScene) {
            for (byte anALevelScene : aLevelScene) {
                if(anALevelScene != 0) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * @return map of <Sprite byte, number of occurrences> in current scene
     */
    public Map<Byte, Integer> getTypeOfEnemies() {
        Map<Byte, Integer> sprites = new HashMap<>();
        byte[][] levelScene = env.getEnemiesObservation();
        for (byte[] aLevelScene : levelScene) {
            for (byte anALevelScene : aLevelScene) {
                if(anALevelScene != 0) {
                    sprites.merge(anALevelScene, 1, Integer::sum);
                }
            }
        }
        return sprites;
    }

    /**
     * @return number of coins in current scene
     */
    public int getNumberOfCoins() {
        int count = 0;
        byte[][] levelScene = env.getLevelSceneObservationZ(0);
        for (byte[] aLevelScene : levelScene) {
            for (byte anALevelScene : aLevelScene) {
                if(anALevelScene == 34) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * @return number of question marks in current scene
     */
    public int getQuestionMarkBlock() {
        int count = 0;
        byte[][] levelScene = env.getLevelSceneObservation();
        for (byte[] aLevelScene : levelScene) {
            for (byte anALevelScene : aLevelScene) {
                if(anALevelScene == 21) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * @return if there are any ? blocks behind Mario in current scene
     */
    public boolean getQuestionMarkBehind() {
        byte[][] levelScene = env.getLevelSceneObservation();

        for(int x = 0; x < 11; x++) {
            for (int y = 0; y < 22; y++) {
                if (levelScene[y][x] == 21) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @return if there is a ? block right above Mario in jumping distance
     */
    public boolean getQuestionMarkAbove() {
        byte[][] levelScene = env.getLevelSceneObservation();
        for(int y = 0; y < 11; y++){
            if(levelScene[y][11] == 21 || (levelScene[y][12] == 21)){
                return true;
            }
            if(levelScene[y][12] == 16 && (levelScene[y][13] == 16)) {
                return true;
            }
        }

        return false;
    }

    /**
     * @return if there is a ? block below Mario (he is standing on it)
     */
    public boolean getQuestionMarkBelow() {
        byte[][] levelScene = env.getLevelSceneObservation();
        for(int y = 11; y < 22; y++){
            if(levelScene[y][11] == 21) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return true if close to a gap, false if not
     */
    public boolean getGapDanger() {
        byte[][] levelScene = env.getLevelSceneObservation();
        boolean ground = false;
        for (int x = 12; x < 14; x++) {
            for(int y = 12; y < 22; y++) {
                if (levelScene[y][x] != 0) {
                    ground = true;
                }
            }

        }
        return ground;
    }

    /**
     * @return true if road block ahead, false if not
     */
    public boolean getRoadBlock() {
        byte[][] levelScene = env.getCompleteObservation();
        for(int x = 11; x < 14; x++) {
            for(int y = 0; y < 22; y++){
                if(levelScene[y][x] == 20) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @return if Mario is in front of a pipe
     */
    public boolean getCannonAhead() {
        byte[][] levelScene = env.getCompleteObservation();
        for(int x = 12; x < 13; x++) {
            for(int y = 0; y < 22; y++){
                if(levelScene[y][x] == 12) {
                    return levelScene[y - 1][x] != 20;
                }
            }
        }
        return false;
    }

    /**
     *
     * @return if Mario is standing on a pipe
     */
    private boolean getOnTower() {
        byte[][] levelScene = env.getLevelSceneObservation();
        for(int y = 9; y < 14; y++){
            if(levelScene[y][11] == 20) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return if Mario is in jumping distance to an enemy
     */
    public boolean getEnemyAheadOnLevel() {
        byte[][] levelScene = env.getLevelSceneObservation();
        int mario = 0;

        for(int y = 8; y < 22; y++) {
            if(levelScene[y][11] == -10) {
                mario = y-1;
                y = 22;
            }
        }
        if(mario < 1) {
            return false;
        }
        levelScene = env.getEnemiesObservation();
        for(int x = 11; x < 14; x++) {
            if(levelScene[mario][x] != 0) {
                return true;
            }

        }
        return false;
    }

    /**
     * @return if there is an enemy close to Mario behind him
     */
    public boolean getEnemyBehindOnLevel() {
        byte[][] levelScene = env.getLevelSceneObservation();
        int mario = 0;
        for(int y = 8; y < 22; y++) {
            if(levelScene[y][11] == -10) {
                mario = y-1;
                y = 22;
            }
        }
        levelScene = env.getEnemiesObservation();
        for(int x = 10; x > 7; x--) {
            if(levelScene[mario][x] != 0) {
                return true;
            }

        }
        return false;
    }

    /**
     * @return if there is a Bullet bill to the left of Mario
     */
    public boolean getBulletBillLeft() {
        byte[][] levelScene = env.getEnemiesObservation();

        for(int x = 8; x < 11; x++){
            for(int y = 0; y < 22; y++){
                if (levelScene[y][x] == 8) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @return if there is a Bullet bill to the right of Mario
     */
    public boolean getBulletBillRight() {
        byte[][] levelScene = env.getEnemiesObservation();
        for(int x = 12; x < 15; x++){
            for(int y = 0; y < 22; y++){
                if (levelScene[y][x] == 8) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @return if there is a Bullet bill above Mario
     */
    public boolean getBulletBillAbove() {
        byte[][] levelScene = env.getEnemiesObservation();
        for(int y = 0; y < 22; y++){
            if (levelScene[y][11] == 8) {
                return true;
            }
        }

        return false;
    }

    /**
     * @return if there is an enemy, a pipe, a gap, or raised ground in
     *  front of Mario and he should jump
     */
    public boolean getShouldJump() {
        byte[][] levelScene = env.getLevelSceneObservation();
        int top = 0;
        //get mario position
        for(int y = 8; y < 22; y++) {
            if(levelScene[y][11] == -10) {
                top = y;
                y = 22;
            }
        }

        //check 1-2 tiles ahead if he should jump
        for(int x = 12; x < 14; x++) {
            for(int y = 8; y < 22; y++){
                if(levelScene[y][x] == -10) {
                    if(top > y) {
                        return true;
                    }
                }
            }
        }

        //check if there is a pipe 1-2 tiles ahead
        for(int y = 0; y < 22; y++){
            if(levelScene[y][12] == 20 || levelScene[y][13] == 20) {
                return true;
            }
        }

        //check if small dip in ground
        for(int y = 8; y < 22; y++){
            if(levelScene[y][12] == -10) {
                if(top < y) {
                    if(levelScene[y][14] == -10 && levelScene[y-1][14] == 0) {
                        return true;
                    }
                }
            }
        }
        return !getGapDanger() || getOnTower();
    }

    /**
     * @return if Mario is right in front of a ledge or pipe
     */
    public boolean getIsMarioStuck(){
        if(!env.isMarioOnGround()) {
            return false;
        }
        //check if there is a pipe 1 tile away
        byte[][] levelScene = env.getLevelSceneObservation();
        for(int y = 8; y < 22; y++){
            if(levelScene[y][12] == 20) { //|| levelScene[y][13] == 20) {
                return true;
            }
        }
        //check if there is a ledge 1 tile away
        int top = 0;
        for(int y = 8; y < 22; y++) {
            if(levelScene[y][12] == -10) {
                top = y;
                y = 22;
            }
        }
        for(int y = 8; y < 22; y++) {
            if(levelScene[y][11] == -10) {
                return y > top;
            }
        }
        return false;
    }

    /**
     * @return if Mario has enough room to run and jump
     */
    public boolean getIsMarioFarEnough() {
        byte[][] levelScene = env.getLevelSceneObservation();
        int top = 0;
        for(int y = 8; y < 22; y++) {
            if(levelScene[y][11] == -10) {
                top = y;
                y = 22;
            }
        }
        if(top == 0) {
            return true;
        }
        //check 2 tiles ahead of him
        if(levelScene[top-1][12] == 0) { //&& levelScene[top-1][13] == 0) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * @return if Mario is in between two pipes
     */
    public boolean getIsMarioSandwich() {
        byte[][] levelScene = env.getCompleteObservation();
        for(int y = 0; y < 22; y++) {
            if ((levelScene[y][10] == 20 || levelScene[y][9] == 20 || levelScene[y][8] == 20) &&
                    (levelScene[y][12] == 20 || levelScene[y][13] == 20 || levelScene[y][14] == 20)) {
                return true;
            }
        }
        return false;
    }

}
