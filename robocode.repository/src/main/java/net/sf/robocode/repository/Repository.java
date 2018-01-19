/**
 * Copyright (c) 2001-2017 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://robocode.sourceforge.net/license/epl-v10.html
 */
package net.sf.robocode.repository;


import net.sf.robocode.io.FileUtil;
import net.sf.robocode.io.Logger;
import net.sf.robocode.repository.items.IRepositoryItem;
import net.sf.robocode.repository.root.BaseRoot;
import net.sf.robocode.repository.root.IRepositoryRoot;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Repository containing robot and team repositoryItems.
 * 
 * @author Pavel Savara (original)
 * @author Flemming N. Larsen (contributor)
 * @author Thales B.V. /Werner Struis (contributor naval)
 */
public class Repository implements IRepository {

	private Map<String, IRepositoryRoot> roots = new ConcurrentHashMap<String, IRepositoryRoot>();
	private Map<String, IRepositoryItem> repositoryItems = new ConcurrentHashMap<String, IRepositoryItem>();
	private Map<String, IRepositoryItem> removedItems = new ConcurrentHashMap<String, IRepositoryItem>();
	private Map<String, IRepositoryItem> competitionItems = new ConcurrentHashMap<String, IRepositoryItem>();
	private Map<String, IRepositoryItem> removedContestants = new ConcurrentHashMap<String, IRepositoryItem>();

	/**
	 * {@inheritDoc}
	 */
	public void save(OutputStream out) {
		Set<IRepositoryItem> uniqueItems = new HashSet<IRepositoryItem>();
		Set<IRepositoryRoot> uniqueRoots = new HashSet<IRepositoryRoot>();

		for (IRepositoryItem repositoryItem : repositoryItems.values()) {
			uniqueItems.add(repositoryItem);
		}

		for (IRepositoryRoot root : roots.values()) {
			uniqueRoots.add(root);
		}

		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(out);
			oos.writeObject(uniqueRoots);
			oos.writeObject(uniqueItems);
		} catch (IOException e) {
			Logger.logError("Can't save robot database", e);
		} finally {
			FileUtil.cleanupStream(oos);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public void load(InputStream in) {
		Set<IRepositoryItem> uniqueItems;
		Set<IRepositoryRoot> uniqueRoots;

		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(in);

			uniqueRoots = (Set<IRepositoryRoot>) ois.readObject();
			uniqueItems = (Set<IRepositoryItem>) ois.readObject();

			for (IRepositoryRoot root : uniqueRoots) {
				((BaseRoot) root).setRepository(this);

				String key = root.getURL().toString();
				key = URLDecoder.decode(key, "UTF-8");

				roots.put(key, root);
			}
			for (IRepositoryItem repositoryItem : uniqueItems) {
				addOrUpdateItem(repositoryItem);
			}
		} catch (IOException e) {
			Logger.logError("Can't load robot database: " + e.getMessage());
		} catch (ClassNotFoundException e) {
			Logger.logError("Can't load robot database: " + e.getMessage());
		} finally {
			FileUtil.cleanupStream(ois);
		}
	}

	public Map<String, IRepositoryItem> getRemovedItems() {
		return Collections.unmodifiableMap(removedItems);
	}

	/**
	 * {@inheritDoc}
	 */
	public void addOrUpdateItem(IRepositoryItem repositoryItem) {
		Collection<String> friendlyUrls = repositoryItem.getFriendlyURLs();
		if (friendlyUrls != null) {
			// Add or update the item so it can be found using later using any friendly URL
			for (String friendly : friendlyUrls) {
				if (friendly != null) {
					IRepositoryItem existingItem = repositoryItems.get(friendly);
					// Add the item if it does not exist already, or update it if the version is newer
					// than the existing item.
					if (existingItem == null || repositoryItem.compareTo(competitionItems.get(friendly)) > 0) {

						String pkg = (((IRobotSpecItem) repositoryItem).getFullPackage());
//						System.out.println(pkg);
//						if (((IRobotSpecItem) repositoryItem).getFullPackage() != null && ((IRobotSpecItem) repositoryItem).getFullPackage().contains("competition")) {
//							competitionItems.put(friendly, repositoryItem);
//						}
						if (existingItem == null || repositoryItem.compareTo(existingItem) > 0) {
							//or endwith, or equals

							repositoryItems.put(friendly, repositoryItem);
						}
					}
				}
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public IRepositoryItem getItem(String friendlyUrl) {
		IRepositoryItem repositoryItem = repositoryItems.get(friendlyUrl);
		if (repositoryItem == null) {
			repositoryItem = removedItems.get(friendlyUrl);
		}
		return repositoryItem;
	}

	/**
	 * {@inheritDoc}
	 */
	public Map<String, IRepositoryItem> getItems() {
		return Collections.unmodifiableMap(repositoryItems);
	}

	/**
	 * {@inheritDoc}
	 */
	public Map<String, IRepositoryItem> getContestants(){
		return Collections.unmodifiableMap(competitionItems);
	}

	/**
	 * {@inheritDoc}
	 */
	public Map<String, IRepositoryRoot> getRoots() {
		return Collections.unmodifiableMap(roots);
	}

	/**
	 * {@inheritDoc}
	 */
	public void removeRoot(String friendlyUrl) {
		roots.remove(friendlyUrl);
	}

	public boolean removeRobot(String className){
		int cur = repositoryItems.size();
		for(Map.Entry<String, IRepositoryItem> s : repositoryItems.entrySet()){
			if(s.getKey().endsWith(className) || s.getKey().endsWith(className + "*")){
				try {
//					removedContestants.put(s.getKey(), competitionItems.get(s.getKey()));

					//delete the class file if it exists
					Files.deleteIfExists(Paths.get(s.getValue().getItemURL().toURI()));


					//delete the java file if it exists
					URI javaFileURI = new URI(s.getValue().getItemURL().toURI().toString().replaceFirst(className + ".class", className + ".java"));
					Files.deleteIfExists(Paths.get(javaFileURI));

					repositoryItems.remove(s.getKey());
				} catch (IOException e) {
					e.printStackTrace();
				} catch (URISyntaxException e) {
					e.printStackTrace();
				}
			}
		}
		return repositoryItems.size() != cur;
//		removedItems.put(friendlyUrl, competitionItems.get(friendlyUrl));
//		competitionItems.remove(friendlyUrl);
	}

	/**
	 * {@inheritDoc}
	 */
	public void removeItemsFromRoot(IRepositoryRoot root) {
		Collection<Map.Entry<String, IRepositoryItem>> itemsToRemove = new ArrayList<Map.Entry<String, IRepositoryItem>>();

		for (Map.Entry<String, IRepositoryItem> entry : repositoryItems.entrySet()) {
			if (entry.getValue().getRoot().equals(root)) {
				itemsToRemove.add(entry);
			}
		}

		for (Map.Entry<String, IRepositoryItem> entry : itemsToRemove) {
			String key = entry.getKey();

			removedItems.put(key, entry.getValue());
			repositoryItems.remove(key);
		}
	}

	/**
	 * Replaces the repository roots with new repository roots.
	 *
	 * @param newRoots is the new repository roots for this repository.
	 */
	// Only for the RepositoryManager
	public void setRoots(Map<String, IRepositoryRoot> newRoots) {
		
		// Remove all items from current roots
		for (IRepositoryRoot root : roots.values()) {
			if (!newRoots.containsKey(root.getURL().toString())) {
				removeItemsFromRoot(root);
			}
		}
	
		// Set the new roots
		roots = newRoots;

		// Clear items to be removed
		removedItems.clear(); 
	}
}
