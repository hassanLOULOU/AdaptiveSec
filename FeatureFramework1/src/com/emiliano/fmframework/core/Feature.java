package com.emiliano.fmframework.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

// TODO: Auto-generated Javadoc
/**
 * The class Feature represents a selectable element of a model. A feature is
 * compound by an identifier name, an optional set of attributes (each attribute
 * with a name and Object value), and an optional FeatureAction. element.
 *
 * @param <SoftElement>
 *            the generic type
 */
public class Feature {

	/** The name. */
	private String name;

	/** The attributes. */
	private Map<String, Object> attributes;

	/** The feature action. */
	private FeatureAction action;

	private Object softElement;

	/**
	 * Instantiates a new feature.
	 *
	 * @param name
	 *            the feature name
	 */
	public Feature(String name) {
		this(name, null, null);
	}

	/**
	 * Instantiates a new feature with an action.
	 *
	 * @param name
	 *            the feature name
	 * @param action
	 *            the feature action
	 */
	public Feature(String name, FeatureAction action) {
		this(name, action, null);
	}

	/**
	 * Instantiates a new feature with a software element.
	 *
	 * @param name
	 *            the feature name
	 * @param softElement
	 *            the software element
	 */
	public Feature(String name, Object softElement) {
		this(name, null, softElement);
	}

	/**
	 * Instantiates a new feature with an action and software element.
	 *
	 * @param name
	 *            the feature name
	 * @param action
	 *            the feature action
	 * @param softElement
	 *            the software element
	 */
	public Feature(String name, FeatureAction action, Object softElement) {
		this.name = name;
		this.attributes = new HashMap<String, Object>();
		this.action = action;
		this.softElement = softElement;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name
	 *            the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the feature action.
	 *
	 * @return the action
	 */
	public FeatureAction getAction() {
		return action;
	}

	/**
	 * Sets the feature action.
	 *
	 * @param action
	 *            the new action
	 */
	public void setAction(FeatureAction action) {
		this.action = action;
	}

	/**
	 * Checks if it is an abstract feature, i.e. it does not contain a feature
	 * action.
	 *
	 * @return true, if it is an abstract feature, or false, if it is a concrete
	 *         feature.
	 */
	public boolean isAbstractFeature() {
		// return this.action == null;
		return this.softElement == null;
	}

	/**
	 * Contains attribute.
	 *
	 * @param name
	 *            the name
	 * @return true, if successful
	 */
	public boolean containsAttribute(String name) {
		return this.attributes.containsKey(name);
	}

	/**
	 * Put attribute.
	 *
	 * @param name
	 *            the name
	 * @param value
	 *            the value
	 * @return the object
	 */
	public Object addAttribute(String name, Object value) {
		return this.attributes.put(name, value);
	}

	/**
	 * Gets the attribute.
	 *
	 * @param name
	 *            the name
	 * @return the attribute
	 */
	public Object getAttribute(String name) {
		return this.attributes.get(name);
	}

	/**
	 * Removes the attribute.
	 *
	 * @param name
	 *            the name
	 * @return the object
	 */
	public Object removeAttribute(String name) {
		return this.attributes.remove(name);
	}

	/**
	 * Gets the attributes.
	 *
	 * @return the attributes
	 */
	public Set<Entry<String, Object>> getAttributes() {
		return this.attributes.entrySet();
	}

	/**
	 * Apply action.
	 *
	 * @param softElement
	 *            the soft element
	 * @return the soft element
	 */
	public Object applyAction(Object softElement) {
		if (this.action != null)
			return this.action.applyAction(softElement);
		else
			return softElement;
	}

	/**
	 * Disapply action.
	 *
	 * @param softElement
	 *            the soft element
	 * @return the soft element
	 */
	public Object disapplyAction(Object softElement) {
		if (this.action != null)
			return this.action.disapplyAction(softElement);
		else
			return softElement;
	}

	public Object getSoftElement() {
		return softElement;
	}

	public void setSoftElement(Object softElement) {
		this.softElement = softElement;
	}

	@Override
	public String toString() {
		return this.name+ " "+this.attributes;
	}
	
}
