package games.stendhal.client.actions;

import java.util.LinkedHashMap;
import java.util.Map;

import games.stendhal.client.ClientSingletonRepository;
import marauroa.common.game.RPAction;

public class DefaultAction implements SlashAction{
	
	LinkedHashMap<String, String> Fixed;
	LinkedHashMap<String, Integer> arg1;
	String arg2;
	int minParameters, maxParameters;
	String type;

	public DefaultAction(String type, int minParameters, int maxParameters, LinkedHashMap<String, String> fixedmap,
			LinkedHashMap<String, Integer> arg1map, String arg2var) {
		
		this.type = type;
		this.Fixed = fixedmap;
		this.arg1 = arg1map;
		this.arg2 = arg2var;
		this.minParameters = minParameters;
		this.maxParameters = maxParameters;
	}
	
	@Override
	public boolean execute(final String[] args1List, final String arg2var) {
		
		final RPAction defaultAction = new RPAction();
		
		if (this.Fixed != null && this.Fixed.size() > 0) {
			for (Map.Entry<String, String> FixedAttr : Fixed.entrySet()) {
				defaultAction.put(FixedAttr.getKey(), FixedAttr.getValue());
			}
		}
		
		if (this.arg1 != null && this.arg1.size() > 0) {
			if (args1List == null && this.minParameters > 0)
				return false;
			for (Map.Entry<String, Integer> arg1Attr : arg1.entrySet()) {
				defaultAction.put(arg1Attr.getKey(), args1List[arg1Attr.getValue()]);
			}
		}
		
		if (this.arg2 != null) {
			
			if (arg2var != null && arg2var.length() > 0)
				defaultAction.put(this.arg2, arg2var);
			else
				return false;
		}
		
		ClientSingletonRepository.getClientFramework().send(defaultAction);
		return true;
	}
	
	@Override
	public int getMinimumParameters() {
		return this.minParameters;
	}
	
	@Override
	public int getMaximumParameters() {
		return this.maxParameters;
	}
	
	@Override 
	public String toString() {
		
		return this.type.substring(0,1).toUpperCase() + this.type.substring(1) + "Action";
	}

}
