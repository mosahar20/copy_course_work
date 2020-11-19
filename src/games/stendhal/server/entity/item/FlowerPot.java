
package games.stendhal.server.entity.item;

import java.util.Map;

import games.stendhal.server.core.events.TurnNotifier;
import games.stendhal.server.entity.RPEntity;
import games.stendhal.server.entity.mapstuff.spawner.FlowerPotGrower;
import games.stendhal.server.entity.item.Seed;

public class FlowerPot extends Seed{
	
	public FlowerPot(final FlowerPot item) {
		super(item);
	}
	
	//Creates a new Flower Pot
	public FlowerPot(final String name, final String clazz, final String subclass, final Map<String, String> attributes) {
		super(name, clazz, subclass, attributes);
	}
	
	@Override
	public boolean onUsed(final RPEntity user) {
		
		if (!this.isContained()) {
			if(this.nextTo(user)) {
				// the infostring of the seed stores what it should grow
				final String infostring = this.getInfoString();
				FlowerPotGrower flowerPotGrower;
				// choose the default flower grower if there is none set
				if (infostring == null) {
					flowerPotGrower = new FlowerPotGrower();
				} else {
					flowerPotGrower = new FlowerPotGrower(this.getInfoString());
				}
				user.getZone().add(flowerPotGrower);
				// add the FlowerGrower where the seed was on the ground
				flowerPotGrower.setPosition(this.getX(), this.getY());
				// The first stage of growth happens almost immediately
				TurnNotifier.get().notifyInTurns(3, flowerPotGrower);
				// remove the seed now that it is planted
				this.removeOne();
				return true;	
			}
		}

		user.sendPrivateText("You have to put the " + this.getName() + " on the ground to plant it, silly!");
		return false;
	}
	
}