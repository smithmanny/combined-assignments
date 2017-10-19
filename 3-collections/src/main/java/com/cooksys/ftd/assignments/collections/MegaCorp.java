package com.cooksys.ftd.assignments.collections;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.cooksys.ftd.assignments.collections.hierarchy.Hierarchy;
import com.cooksys.ftd.assignments.collections.model.Capitalist;
import com.cooksys.ftd.assignments.collections.model.FatCat;

public class MegaCorp implements Hierarchy<Capitalist, FatCat> {

	private Set<Capitalist> capitalists = new HashSet<Capitalist>();

	/**
	 * Adds a given element to the hierarchy.
	 * <p>
	 * If the given element is already present in the hierarchy, do not add it
	 * and return false
	 * <p>
	 * If the given element has a parent and the parent is not part of the
	 * hierarchy, add the parent and then add the given element
	 * <p>
	 * If the given element has no parent but is a Parent itself, add it to the
	 * hierarchy
	 * <p>
	 * If the given element has no parent and is not a Parent itself, do not add
	 * it and return false
	 *
	 * @param capitalist
	 *            the element to add to the hierarchy
	 * @return true if the element was added successfully, false otherwise
	 */
	@Override
	public boolean add(Capitalist capitalist) {
		if ((capitalist == null) || (this.capitalists.contains(capitalist)))
			return false;

		if (capitalist.hasParent()) {
			this.add(capitalist.getParent());

			return this.capitalists.add(capitalist);
		} else {
			if (capitalist instanceof FatCat) {
				return this.capitalists.add(capitalist);
			}
		}

		return false;
	}

	/**
	 * @param capitalist
	 *            the element to search for
	 * @return true if the element has been added to the hierarchy, false
	 *         otherwise
	 */
	@Override
	public boolean has(Capitalist capitalist) {
		return this.capitalists.contains(capitalist);
	}

	/**
	 * @return all elements in the hierarchy, or an empty set if no elements
	 *         have been added to the hierarchy
	 */
	@Override
	public Set<Capitalist> getElements() {
		Set<Capitalist> rc = new HashSet<Capitalist>();

		for (Capitalist capitalist : this.capitalists) {
			rc.add(capitalist);
		}

		return rc;
	}

	/**
	 * @return all parent elements in the hierarchy, or an empty set if no
	 *         parents have been added to the hierarchy
	 */
	@Override
	public Set<FatCat> getParents() {
		Set<FatCat> fatCats = new HashSet<FatCat>();

		for (Capitalist capitalist : this.capitalists) {
			if (capitalist instanceof FatCat) {
				fatCats.add((FatCat) capitalist);
			}
		}

		return fatCats;
	}

	/**
	 * @param fatCat
	 *            the parent whose children need to be returned
	 * @return all elements in the hierarchy that have the given parent as a
	 *         direct parent, or an empty set if the parent is not present in
	 *         the hierarchy or if there are no children for the given parent
	 */
	@Override
	public Set<Capitalist> getChildren(FatCat fatCat) {
		Set<Capitalist> children = new HashSet<Capitalist>();

		for (Capitalist capitalist : this.capitalists) {
			if (capitalist.hasParent() && capitalist.getParent().equals(fatCat)) {
				children.add(capitalist);
			}
		}

		return children;
	}

	/**
	 * @return a map in which the keys represent the parent elements in the
	 *         hierarchy, and the each value is a set of the direct children of
	 *         the associate parent, or an empty map if the hierarchy is empty.
	 */
	@Override
	public Map<FatCat, Set<Capitalist>> getHierarchy() {
		Map<FatCat, Set<Capitalist>> map = new HashMap<>();

		for (FatCat fatCat : this.getParents()) {
			Set<Capitalist> children = this.getChildren(fatCat);

			map.put(fatCat, children);
		}

		return map;
	}

	/**
	 * @param capitalist
	 * @return the parent chain of the given element, starting with its direct
	 *         parent, then its parent's parent, etc, or an empty list if the
	 *         given element has no parent or if its parent is not in the
	 *         hierarchy
	 */
	@Override
	public List<FatCat> getParentChain(Capitalist capitalist) {
		List<FatCat> fatCats = new LinkedList<>();

		if ((capitalist != null) && this.has(capitalist)) {
			FatCat parent = capitalist.getParent();

			while (parent != null) {
				fatCats.add(parent);
				parent = parent.getParent();
			}
		}

		return fatCats;
	}
}
