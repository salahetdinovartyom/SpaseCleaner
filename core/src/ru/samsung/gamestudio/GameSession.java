package ru.samsung.gamestudio;

import com.badlogic.gdx.utils.TimeUtils;

import static ru.samsung.gamestudio.GameSettings.*;

import java.util.ArrayList;

import ru.samsung.gamestudio.managers.MemoryManager;

public class GameSession {
    long sessionStartTime,nextTrashSpawnTime,sessionPauseTime;
    public GameState state;
    private int score;
    int destructedTrashNumber;
    public void startGame() {
        destructedTrashNumber=0;
        state=GameState.PLAYING;
        setScore(0);
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

    public void setScore(int score) {
        this.score = score;
    }

    public boolean shouldSpawnTrash() {
        if (nextTrashSpawnTime<=TimeUtils.millis()) {
            nextTrashSpawnTime=TimeUtils.millis()+(long) (STARTING_TRASH_APPEARANCE_COOL_DOWN*getTrashPeriodCoolDown());
            return true;
        }
        return false;
    }
    private float getTrashPeriodCoolDown() {
        return (float) Math.exp(-0.001*(TimeUtils.millis()-sessionStartTime)/100000);
    }
    public void destructionRegistration() {
        destructedTrashNumber+=1;
    }
    public void updateScore() {
        score=(int) (TimeUtils.millis()-sessionStartTime)/500+destructedTrashNumber*100;
    }
    public int getScore() {
        return score;
    }
    public void endGame() {
        state=GameState.ENDED;
        updateScore();
        ArrayList<Integer> recordsTable= MemoryManager.loadRecordsTable();
        if (recordsTable==null) recordsTable=new ArrayList<>();
        int foundIdx=0;
        for (;foundIdx<recordsTable.size();foundIdx++) {
            if (recordsTable.get(foundIdx)<getScore()+100) break;
        }
        recordsTable.add(foundIdx,getScore()+100);
        MemoryManager.saveTableOfRecords(recordsTable);
        setScore(0);
    }


}
