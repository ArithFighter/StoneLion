package com.arithfighter.ccg;

import com.arithfighter.ccg.entity.CharacterList;
import com.arithfighter.ccg.entity.GameDataAccessor;
import com.arithfighter.ccg.entity.Player;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Game {
    private final Player[] players;
    private final GameComponent gameComponent;
    private final GameDataAccessor dataAccessor;
    private final int characterQuantity = CharacterList.values().length;

    public Game(Texture[] textures, Texture[] cards){
        dataAccessor = new GameDataAccessor();

        players = new Player[characterQuantity];

        addPlayers(textures, cards);

        gameComponent = new GameComponent(textures, dataAccessor);
    }

    private void addPlayers(Texture[] textures, Texture[] cards) {
        SkillHandler skillHandler = new SkillHandler();

        for (int i = 0; i < characterQuantity; i++)
            players[i] = new Player(
                    textures,
                    cards,
                    CharacterList.values()[i]) {
                @Override
                public void doWhenCardPlayed() {
                    dataAccessor.updatePlayTimes();
                }

                @Override
                public void castSkill(CharacterList character) {
                    skillHandler.cast(character, gameComponent.getNumberBoxDisplacer());
                }
            };
    }

    public boolean isReturnToMenu(){
        return gameComponent.isReturnToMenu();
    }

    public void init(){
        gameComponent.init();
    }

    public void update(int mouseX, int mouseY){
        gameComponent.update(mouseX, mouseY);
    }

    public void draw(SpriteBatch batch){
        gameComponent.draw(batch);
    }

    public void setCurrentPlayerInGame(int i){
        gameComponent.setPlayer(players[i]);
    }

    public void touchDown(int mouseX, int mouseY){
        gameComponent.getPlayer().activateCard(mouseX, mouseY);

        gameComponent.getReturnButton().activate(mouseX, mouseY);
    }

    public void touchDragged(int mouseX, int mouseY){
        gameComponent.getPlayer().updateWhenDrag(mouseX, mouseY);
    }

    public void touchUp(int mouseX, int mouseY){
        gameComponent.getBoardArea().playCardOnBoard(mouseX, mouseY);

        gameComponent.getReturnButton().deactivate();
    }

    public void dispose(){
        dataAccessor.dispose();
        gameComponent.dispose();
        for (Player player : players) player.dispose();
    }
}
