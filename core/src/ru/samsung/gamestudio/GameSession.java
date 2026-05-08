package ru.samsung.gamestudio;

import com.badlogic.gdx.utils.TimeUtils;

import static ru.samsung.gamestudio.GameSettings.*;

public class GameSession {
    long sessionStartTime,nextTrashSpawnTime,sessionPauseTime;
    public GameState state;


    public void startGame() {
        state=GameState.PLAYING;
        sessionStartTime=TimeUtils.millis();
        nextTrashSpawnTime=sessionStartTime+(long) (STARTING_TRASH_APPEARANCE_COOL_DOWN*getTrashPeriodCoolDown());
    }
    public void pauseGame() {
        state=GameState.PAUSED;
        sessionPauseTime=TimeUtils.millis();
    }
    public void resumeGame() {
        state=GameState.PLAYING;
        sessionStartTime += TimeUtils.millis()-sessionPauseTime;

    }
    public boolean shouldSpawnTrash() {
        if (nextTrashSpawnTime<=TimeUtils.millis()) {
            nextTrashSpawnTime=TimeUtils.millis()+(long) (STARTING_TRASH_APPEARANCE_COOL_DOWN*getTrashPeriodCoolDown());
            return true;
        }
        return false;
    }
    private float getTrashPeriodCoolDown() {
        return (float) Math.exp(-0.001*(TimeUtils.millis()-sessionStartTime)/1000);
    }

}
