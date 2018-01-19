package robocode.naval;

import net.sf.robocode.serialization.ISerializableHelper;
import net.sf.robocode.serialization.RbSerializer;
import robocode.naval.Components.ComponentBase;
import robocode.naval.interfaces.componentInterfaces.IComponent;

import java.awt.*;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages the different components that can be assigned to a ship.
 * @author Thales B.V. / Jiri Waning / Thomas Hakkers
 * @since 1.8.3.0 Alpha 1
 * @version 0.3
 */
public class ComponentManager implements Serializable {


	/* Slot numbers*/
	public static int	SLOT1 = 0,
						SLOT2 = 1,
						SLOT3 = 2,
						SLOT4 = 3;

	private static Map<ShipType, Map<Integer, Double>> offset = new HashMap<ShipType, Map<Integer, Double>>();
		
	// ======================================================================
	//  Fields
	// ======================================================================
	private static final long serialVersionUID = 6105556384436141732L;

	/**
	 *
	 */

	/**
	 * The maximum amount of components that can be added to a ship.
	 */
	private final ShipType type;
	private final int max_components;
	
	/**
	 * The list containing all the components.
	 */
	private IComponent[] components;

	/**
	 * Get the Y offset for the slotindex
	 * used to place components at the right position
	 */
	public static double getSlotOffset(int slotIndex, ShipType type){
		return offset.get(type).get(slotIndex);
	}

	/**
	 * set the slot at slotindex = slotIndex
	 * @param slotIndex slotIndex
	 * @param component Component to set
	 */
	public void setSlot(int slotIndex, IComponent component){
		if(isValidIndex(slotIndex)){
			components[slotIndex] = component;
		}
	}

	/**
	 * Get the slot at slotindex = slotIndex
	 * @param slotIndex slotIndex to get
	 */
	public IComponent getSlot(int slotIndex){
		if(isValidIndex(slotIndex)){
			return components[slotIndex];
		}
		return null;
	}
	
	

	// ======================================================================
	//  Constructors
	// ======================================================================
	public ComponentManager(ShipType type) {
		this.type = type;
		this.max_components = type.getMaxSlots();
		this.components = new IComponent[max_components];

		initOffsets(type);
	}

	private void initOffsets(ShipType type){
		Map<Integer, Double> values = new HashMap<Integer, Double>();
		values.put(SLOT1, type.getfWeaponOffset());
		values.put(SLOT2, type.getRadarOffset());
		values.put(SLOT3, type.getmWeaponOffset());
		values.put(SLOT4, type.getbWeaponOffset());

		offset.put(type, values);
	}
	
	// Is being used to make copies of an ComponentManager instance.
	private ComponentManager(ShipType type, IComponent[] components, int max_components) {
		this.type = type;
		this.max_components = max_components;
		this.components = components;
	}
		
		

	
	// ======================================================================
	//  Miscellaneous
	// ======================================================================
	
	/**
	 * Get a copy of the given {@code ComponentManager}.
	 * @param manager The manager that has to be copied.
	 * @return A copy of the manager.
	 */
	public static final ComponentManager copy(ComponentManager manager) {
		if (manager == null) {
			return null;
		}
		return new ComponentManager(manager.type, manager.components, manager.max_components);
	}

	/**
	 * Get the component list
	 * @return component list
	 */
	public IComponent[] getComponentsList(){
		return components;
	}

	/**
	 * {@inheritDoc}
	 */
	public <T extends IComponent> T getComponent(Class<T> componentClass) {
		for (IComponent component: components) {
			if (component == null) {
				continue;
			}

			// componentClass.class and its super classes
			if (componentClass.isAssignableFrom(component.getClass()) ||
					component.getClass().equals(componentClass)) {
				@SuppressWarnings("unchecked") // Object ==> T
						T obj = (T)component;
				return obj;
			}
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public <T extends IComponent> T[] getComponents(Class<T> componentClass) {
		@SuppressWarnings("unchecked") // Object ==> Array
				T[] components = (T[]) Array.newInstance(componentClass, count(componentClass));

		// componentClass.class and its super classes
		ArrayList<T> list = new ArrayList<T>();
		for (IComponent component: this.components) {
			if (component == null) {
				continue;
			}

			if (componentClass.isAssignableFrom(component.getClass()) ||
					component.getClass().equals(componentClass)) {
				@SuppressWarnings("unchecked")
				T obj = (T)component;
				list.add(obj);
			}
		}

		list.toArray(components);
		return components;
	}

	/**
	 * Counts the amount of components that matches the given class.
	 * @param componentClass The class of the components.
	 * @return The amount of components of the specified class.
	 */
	private <T extends IComponent> int count(Class<T> componentClass) {
		int total = 0;

		for (IComponent component: components) {
			if (component == null) {
				continue;
			}

			// componentClass.class and its super classes
			if (componentClass.isAssignableFrom(component.getClass()) ||
					component.getClass().equals(componentClass)) {
				total++;
			}
		}

		return total;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public int size() {
		return components.length;
	}


	/**
	 * Set the color of the component at the specified index.
	 * @param index The index of the component.
	 * @param color The color that the component should have.
	 */
	public void setColor(int index, Color color) {
		if (isValidIndex(index)) {
			components[index].setColor(color);
		}
	}

	private boolean isValidIndex(int index){
		return index < max_components;
	}


	
	/**
	 * Get the color of the component at the specified index.
	 * @param index The index of the component.
	 * @return The color of the component.
	 */
	public Color getColor(int index) {
		// By default the return value will be white.
		Color color = Color.WHITE;
		
		// When the index is valid we get the color of the component itself.
		if (isValidIndex(index)) {
			color = components[index].getColor();
		}
		
		return color;
	}
















	static ISerializableHelper createHiddenSerializer() {
		return new SerializableHelper();
	}

	private static class SerializableHelper implements ISerializableHelper {
		public int sizeOf(RbSerializer serializer, Object object) {
			int size = 0;
			ComponentManager obj = (ComponentManager) object;
			size += RbSerializer.SIZEOF_INT;
			for(IComponent component : obj.components){
				size += serializer.sizeOf(component.getSerializeType(), component);
			}
			
			return RbSerializer.SIZEOF_TYPEINFO + RbSerializer.SIZEOF_INT + size ;
		}
		

		public void serialize(RbSerializer serializer, ByteBuffer buffer, Object object) {
			ComponentManager obj = (ComponentManager) object;
			serializer.serialize(buffer, obj.size());
			serializer.serialize(buffer, obj.type.getID());
			serializer.serialize(buffer, obj.max_components);
			for(IComponent component : obj.components){
				serializer.serialize(buffer, component.getSerializeType(), object);
			}
		}

		public Object deserialize(RbSerializer serializer, ByteBuffer buffer) {
			int size = serializer.deserializeInt(buffer);
			int typeID = serializer.deserializeInt(buffer);
			int max_components = serializer.deserializeInt(buffer);
			IComponent[] components = new IComponent[max_components];
			for(int i = 0; i < size; ++i){
				ComponentBase component = (ComponentBase)serializer.deserializeAny(buffer);
				components[i] = component;
			}
			return new ComponentManager(ShipType.getTypeByID(typeID), components, max_components);
		}
	}
}