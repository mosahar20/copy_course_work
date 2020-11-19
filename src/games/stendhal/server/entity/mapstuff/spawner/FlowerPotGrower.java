
package games.stendhal.server.entity.mapstuff.spawner;

import games.stendhal.common.Rand;
import games.stendhal.common.grammar.Grammar;
import games.stendhal.server.core.engine.StendhalRPObjectFactory;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.Entity;
import games.stendhal.server.entity.item.Item;
import marauroa.common.game.RPObject;

/**
 * Is an item producer that destroys itself when item is removed.
 * <p>
 * The standard vegetable grower would restart production cycle after removal of
 * fruit.
 *
 * Fruit is only grown when FlowerGrower is in the same place as an entity in
 * zone that implements FertileGround
 *
 */
public class FlowerPotGrower extends VegetableGrower {
	/** 5 hours for one growing step */
	private static final int GROW_TIME_TURNS = 60000;
	/** Maximum ripeness of flowers */
	private static final int MAX_RIPENESS = 4;
	private static final String GROWER_NAME = "lilia";
    /** The description depends upon the ripeness of the flower grower */
	private final String[] description = {
			"You see something which has just been planted.",
			"Something is sprouting from the ground.",
			"A plant is growing here, and you can already see foliage.",
			"You see a plant growing a " + Grammar.fullForm(getVegetableName())
					+ ", it is nearly at full maturity.",
			"You see a fully grown " + Grammar.fullForm(getVegetableName())
					+ ", ready to pull from the ground." };

	/**
	 * Constructor for loading FlowerGrower from the stored zone used by
	 * StendhalRPObjectFactory.
	 *
	 * @see StendhalRPObjectFactory
	 *
	 * @param object
	 *            the restored object from db
	 * @param itemname
	 *            the item to grow
	 */
	public FlowerPotGrower(final RPObject object, final String itemname) {
		super(object, itemname, MAX_RIPENESS, GROW_TIME_TURNS);
		store();
	}

	/**
	 * Constructor.
	 *
	 * Default FlowerGrower produces lilia.
	 */
	public FlowerPotGrower() {
		this(GROWER_NAME);
		store();
	}

	/**
	 * Constructor of a FlowerGrower growing an item with the name specified in
	 * infostring.
	 *
	 * @param infoString
	 *            the name of the item to produce
	 *
	 */
	public FlowerPotGrower(final String infoString) {
		super(infoString);
		setMaxRipeness(MAX_RIPENESS);
		meanTurnsForRegrow = GROW_TIME_TURNS;
		store();
	}

	/**
	 * Removes this from world. This method is called when the fruit of this
	 * grower is picked.
	 */
	@Override
	public void onFruitPicked(final Item picked) {
		getZone().remove(this);
		notifyWorldAboutChanges();
	}

	@Override
	protected int getRandomTurnsForRegrow() {
		return Rand.randGaussian(meanTurnsForRegrow, (int) (0.1 * meanTurnsForRegrow));
	}

    /** The description depends upon the ripeness of the flower grower */
	@Override
	public String describe() {
		if ((getRipeness() < 0) || (getRipeness() > getMaxRipeness())) {
			return super.describe();
		} else {
			return description[getRipeness()];
		}
	}

	/**
	 * Checks if this entity is on a free fertile spot.
	 *
     * If yes, the flower can grow. Otherwise it withers and dies.
     *
	 * @return true if there is an item implementing FertileGround in the zone,
	 *         and the position of this is in its area.
	 */
	
	public boolean FlowerGrowerFertileGround() {
		if (this.getZone() == null) {
			return false;
		} else {
			final StendhalRPZone zone = this.getZone();
			boolean passes = false;
			for (Entity entity : zone.getEntitiesAt(getX(), getY())) {
				if (entity instanceof FlowerGrower) {
					if (!equals(entity)) {
						// There's already something else growing here
						return false;
					}
				} else {
						//Must be true as this class is only called by FlowerPots which are fertile.
						passes = true;
					
				}
			}
			return passes;
		}
	}

	@Override
	protected void growNewFruit() {
		if (FlowerGrowerFertileGround()) {
			super.growNewFruit();
		} else {
			if (getZone() != null) {
				getZone().remove(this);
			}
		}
	}
}
