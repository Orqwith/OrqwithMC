package com.orqwith.mc.statistics;

import java.io.File;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.orqwith.mc.TheWalkingDead;

public class PlayerStatistics {
	Player player;
	int mostTimeAlive = 0;
	int mostFoodEaten = 0;
	int mostZombiesKilled = 0;
	int mostPlayersKilled = 0;
	int mostDamageDone = 0;
	int mostDamageTaken = 0;
	
	int currentTimeAlive = 0;
	int currentFoodEaten = 0;
	int currentZombiesKilled = 0;
	int currentPlayersKilled = 0;
	int currentDamageDone = 0;
	int currentDamageTaken = 0;
	
	YamlConfiguration playerStatsYml;
	File playerStatsFile; 
	
	public PlayerStatistics(Player player)
	{
		this.player = player;
		loadStatistics();
		
		playerStatsFile = new File(TheWalkingDead.pluginDirectory.getPath(), player.getName().trim()+".yml");
		
		if(playerStatsYml != null)
		{
			mostTimeAlive = playerStatsYml.getInt("player.mostTimeAlive");
			mostFoodEaten = playerStatsYml.getInt("player.mostFoodEaten");
			mostZombiesKilled = playerStatsYml.getInt("player.mostZombiesKilled");
			mostPlayersKilled = playerStatsYml.getInt("player.mostPlayersKilled");
			mostDamageDone = playerStatsYml.getInt("player.mostDamageDone");
			mostDamageTaken = playerStatsYml.getInt("player.mostDamageTaken");
			
			currentTimeAlive = playerStatsYml.getInt("player.currentTimeAlive");
			currentFoodEaten = playerStatsYml.getInt("player.currentFoodEaten");
			currentZombiesKilled = playerStatsYml.getInt("player.currentZombiesKilled");
			currentPlayersKilled = playerStatsYml.getInt("player.currentPlayersKilled");
			currentDamageDone = playerStatsYml.getInt("player.currentDamageDone");
			currentDamageTaken = playerStatsYml.getInt("player.currentDamageTaken");
		}
	}
	
	public void loadStatistics()
	{
		playerStatsYml = YamlConfiguration.loadConfiguration(playerStatsFile);
	}
	
	public void saveStatistics()
	{
		// set max
		playerStatsYml.set("player.mostTimeAlive", getMax(this.mostTimeAlive, this.currentTimeAlive));
		playerStatsYml.set("player.mostFoodEaten", getMax(this.mostFoodEaten, this.currentFoodEaten));
		playerStatsYml.set("player.mostZombiesKilled", getMax(this.mostZombiesKilled, this.currentZombiesKilled));
		playerStatsYml.set("player.mostPlayersKilled", getMax(this.mostPlayersKilled, this.currentPlayersKilled));
		playerStatsYml.set("player.mostDamageDone", getMax(this.mostDamageDone, this.currentDamageDone));
		playerStatsYml.set("player.mostDamageTaken", getMax(this.mostDamageTaken, this.currentDamageTaken));
		
		if(player.isDead())
		{
			clearStatistics();
		}
		
		// set current values (so that they're not lossed between log in and log out)
		playerStatsYml.set("player.currentTimeAlive", this.currentTimeAlive);
		playerStatsYml.set("player.currentFoodEaten", this.currentFoodEaten);
		playerStatsYml.set("player.currentZombiesKilled", this.currentZombiesKilled);
		playerStatsYml.set("player.currentPlayersKilled", this.currentPlayersKilled);
		playerStatsYml.set("player.currentDamageDone", this.currentDamageDone);
		playerStatsYml.set("player.currentDamageTaken", this.currentDamageTaken);
	}
	
	public void clearStatistics()
	{
		// usually caused by player death
		currentTimeAlive = 0;
		currentFoodEaten = 0;
		currentZombiesKilled = 0;
		currentPlayersKilled = 0;
		currentDamageDone = 0;
		currentDamageTaken = 0;
		
	}
	
	public int getMax(int number1, int number2)
	{
		if(number1 > number2)
		{
			return number1;
		}
		
		return number2;
	}
	
	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public int getMostTimeAlive() {
		return mostTimeAlive;
	}

	public void setMostTimeAlive(int mostTimeAlive) {
		this.mostTimeAlive = mostTimeAlive;
	}

	public int getMostFoodEaten() {
		return mostFoodEaten;
	}

	public void setMostFoodEaten(int mostFoodEaten) {
		this.mostFoodEaten = mostFoodEaten;
	}

	public int getMostZombiesKilled() {
		return mostZombiesKilled;
	}

	public void setMostZombiesKilled(int mostZombiesKilled) {
		this.mostZombiesKilled = mostZombiesKilled;
	}

	public int getMostPlayersKilled() {
		return mostPlayersKilled;
	}

	public void setMostPlayersKilled(int mostPlayersKilled) {
		this.mostPlayersKilled = mostPlayersKilled;
	}

	public int getMostDamageDone() {
		return mostDamageDone;
	}

	public void setMostDamageDone(int mostDamageDone) {
		this.mostDamageDone = mostDamageDone;
	}

	public int getMostDamageTaken() {
		return mostDamageTaken;
	}

	public void setMostDamageTaken(int mostDamageTaken) {
		this.mostDamageTaken = mostDamageTaken;
	}

	public int getCurrentTimeAlive() {
		return currentTimeAlive;
	}

	public void setCurrentTimeAlive(int currentTimeAlive) {
		this.currentTimeAlive = currentTimeAlive;
	}

	public int getCurrentFoodEaten() {
		return currentFoodEaten;
	}

	public void setCurrentFoodEaten(int currentFoodEaten) {
		this.currentFoodEaten = currentFoodEaten;
	}

	public int getCurrentZombiesKilled() {
		return currentZombiesKilled;
	}

	public void setCurrentZombiesKilled(int currentZombiesKilled) {
		this.currentZombiesKilled = currentZombiesKilled;
	}

	public int getCurrentPlayersKilled() {
		return currentPlayersKilled;
	}

	public void setCurrentPlayersKilled(int currentPlayersKilled) {
		this.currentPlayersKilled = currentPlayersKilled;
	}

	public int getCurrentDamageDone() {
		return currentDamageDone;
	}

	public void setCurrentDamageDone(int currentDamageDone) {
		this.currentDamageDone = currentDamageDone;
	}

	public int getCurrentDamageTaken() {
		return currentDamageTaken;
	}

	public void setCurrentDamageTaken(int currentDamageTaken) {
		this.currentDamageTaken = currentDamageTaken;
	}	
}
