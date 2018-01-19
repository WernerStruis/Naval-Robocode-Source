/*******************************************************************************
 * Copyright (c) 2001-2013 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://robocode.sourceforge.net/license/epl-v10.html
 *******************************************************************************/
package net.sf.robocode.ui;


import net.sf.robocode.security.HiddenAccess;
import net.sf.robocode.settings.ISettingsManager;
import net.sf.robocode.ui.gfx.ImageUtil;
import net.sf.robocode.ui.gfx.RenderImage;
import robocode.naval.CannonType;
import robocode.naval.RadarType;
import robocode.naval.ShipType;

import java.awt.*;
import java.util.*;
import java.util.List;


/**
 * @author Mathew A. Nelson (original)
 * @author Flemming N. Larsen (contributor)
 * @author Titus Chen (contributor)
 * @author Thales B.V. /Werner Struis (contributor naval)
 */
public class ImageManager implements IImageManager {
	
	private final ISettingsManager properties;

	private Image[] groundImages;

	private RenderImage[][] explosionRenderImages;
	private RenderImage debriseRenderImage;

	private Image bodyImage;
	private Image gunImage;
	private Image radarImage;
	
	// Naval
//	private Image navalImageBody;
	private Image navalImageCruiserBody;
	private Image navalImageCorvetteBody;
	private Image navalImageCarrierBody;
//	private Image navalImageGun;
	private Image navalImageDoubleGun;
	private Image navalImageSingleGun;

	private Image navalImageLongRadar;
	private Image navalImageShortRadar;

	private Image navalImageMine;
	private Image navalImageMineComponent;
	private Image navalImageMissile;
	private Image navalImageMissileComponent;


	private static final int MAX_NUM_COLORS = 256;

	private HashMap<Integer, RenderImage> robotBodyImageCache;
	private HashMap<Integer, RenderImage> robotGunImageCache;
	private HashMap<Integer, RenderImage> robotRadarImageCache;
	
//	private HashMap<Integer, RenderImage> navalImageCacheBody;
	private HashMap<Integer, RenderImage> navalImageCacheCruiserBody;
	private HashMap<Integer, RenderImage> navalImageCacheCorvetteBody;
	private HashMap<Integer, RenderImage> navalImageCacheCarrierBody;


//	private HashMap<Integer, RenderImage> navalImageCacheGun;

	private HashMap<Integer, RenderImage> navalImageCacheSingleGun;
	private HashMap<Integer, RenderImage> navalImageCacheDoubleGun;

	private HashMap<Integer, RenderImage> navalImageCacheLongRadar;
	private HashMap<Integer, RenderImage> navalImageCacheShortRadar;

	private HashMap<Integer, RenderImage> navalImageCacheMine;
	private HashMap<Integer, RenderImage> navalImageCacheMineComponent;
	private HashMap<Integer, RenderImage> navalImageCacheMissile;
	private HashMap<Integer, RenderImage> navalImageCacheMissileComponent;


	public ImageManager(ISettingsManager properties) {
		this.properties = properties;
	}

	public void initialize() {
		// Note that initialize could be called in order to reset all images (image buffering)

		// Reset image cache
		groundImages = new Image[5];
		explosionRenderImages = null;
		debriseRenderImage = null;
		bodyImage = null;
		gunImage = null;
		radarImage = null;
		robotBodyImageCache = new RenderCache<Integer, RenderImage>();
		robotGunImageCache = new RenderCache<Integer, RenderImage>();
		robotRadarImageCache = new RenderCache<Integer, RenderImage>();

		// Read images into the cache
		getBodyImage();
		getGunImage();
		getRadarImage();
		getExplosionRenderImage(0, 0);
		
		// Naval, reset
		{
//			navalImageBody = null;
			navalImageCruiserBody = null;
			navalImageCorvetteBody = null;
			navalImageCarrierBody = null;
//			navalImageGun = null;

			navalImageDoubleGun = null;
			navalImageSingleGun = null;

			navalImageLongRadar = null;
			navalImageCacheShortRadar = null;

			navalImageMine = null;
			navalImageMineComponent = null;
			navalImageMissile = null;
			
//			navalImageCacheBody = new RenderCache<Integer, RenderImage>();
			navalImageCacheCruiserBody = new RenderCache<Integer, RenderImage>();
			navalImageCacheCorvetteBody = new RenderCache<Integer, RenderImage>();
			navalImageCacheCarrierBody = new RenderCache<Integer, RenderImage>();

//			navalImageCacheGun = new RenderCache<Integer, RenderImage>();
			navalImageCacheDoubleGun = new RenderCache<Integer, RenderImage>();
			navalImageCacheSingleGun = new RenderCache<Integer, RenderImage>();

			navalImageCacheShortRadar = new RenderCache<Integer, RenderImage>();
			navalImageCacheLongRadar = new RenderCache<Integer, RenderImage>();

			navalImageCacheMine = new RenderCache<Integer, RenderImage>();
			navalImageCacheMissile = new RenderCache<Integer, RenderImage>();
			navalImageCacheMineComponent = new RenderCache<Integer, RenderImage>();
			navalImageCacheMissileComponent = new RenderCache<Integer, RenderImage>();


//			getNavalImageBody();
			getNavalImageBody(ShipType.CRUISER);
			getNavalImageBody(ShipType.CORVETTE);
			getNavalImageBody(ShipType.CARRIER);


//			getNavalImageCruiserBody();
//			getNavalImageCorvetteBody();
//			getNavalImageCarrierBody();
//			getNavalImageGun();\
			getNavalImageGun(CannonType.DOUBLE_BARREL);
			getNavalImageGun(CannonType.SINGLE_BARREL);
//			getNavalImageDoubleGun();
//			getNavalImageSingleGun();


			getNavalImageLongRadar();
			getNavalImageShortRadar();

			getNavalImageMine();
			getNavalImageMineComponent();
			getNavalImageMissile();
		}
	}

	public Image getGroundTileImage(int index) {
		if (HiddenAccess.getNaval()) {
			groundImages[index] = getImage("/net/sf/robocode/ui/images/naval/ground/blue_metal_" + index + ".png");
		} else {
			groundImages[index] = getImage("/net/sf/robocode/ui/images/ground/blue_metal/blue_metal_" + index + ".png");
		}
		
		return groundImages[index];
	}

	public RenderImage getExplosionRenderImage(int which, int frame) {
		if (explosionRenderImages == null) {
			int numExplosion, numFrame;
			String filename;

			List<List<RenderImage>> explosions = new ArrayList<List<RenderImage>>();

			boolean done = false;

			for (numExplosion = 1; !done; numExplosion++) {
				List<RenderImage> frames = new ArrayList<RenderImage>();

				for (numFrame = 1;; numFrame++) {
					filename = "/net/sf/robocode/ui/images/explosion/explosion" + numExplosion + '-' + numFrame + ".png";

					if (ImageManager.class.getResource(filename) == null) {
						if (numFrame == 1) {
							done = true;
						} else {
							explosions.add(frames);
						}
						break;
					}

					frames.add(new RenderImage(getImage(filename)));
				}
			}

			numExplosion = explosions.size();
			explosionRenderImages = new RenderImage[numExplosion][];

			for (int i = numExplosion - 1; i >= 0; i--) {
				explosionRenderImages[i] = explosions.get(i).toArray(new RenderImage[explosions.size()]);
			}
		}
		return explosionRenderImages[which][frame];
	}

	public RenderImage getExplosionDebrisRenderImage() {
		if (debriseRenderImage == null) {
			debriseRenderImage = new RenderImage(getImage("/net/sf/robocode/ui/images/ground/explode_debris.png"));
		}
		return debriseRenderImage;
	}

	private Image getImage(String filename) {
		Image image = ImageUtil.getImage(filename);

		if (properties.getOptionsRenderingBufferImages()) {
			image = ImageUtil.getBufferedImage(image);
		}
		return image;
	}

	/**
	 * Gets the body image
	 * Loads from disk if necessary.
	 *Missile
	 * @return the body image
	 */
	private Image getBodyImage() {
		if (bodyImage == null) {
			bodyImage = getImage("/net/sf/robocode/ui/images/body.png");
		}
		return bodyImage;
	}
	private Image getNavalImageBody(ShipType type) {
		switch (type){
			case CRUISER:
				if (navalImageCruiserBody == null) {
					navalImageCruiserBody = getImage("/net/sf/robocode/ui/images/naval/cruiser-body.png");
				}
				return navalImageCruiserBody;
			case CORVETTE:
				if (navalImageCorvetteBody == null) {
					navalImageCorvetteBody = getImage("/net/sf/robocode/ui/images/naval/corvette-body.png");
				}
				return navalImageCorvetteBody;
			case CARRIER:
				if (navalImageCarrierBody == null) {
					navalImageCarrierBody = getImage("/net/sf/robocode/ui/images/naval/carrier-body.png");
				}
				return navalImageCarrierBody;
		}
		//should not get here
		return null;
	}
//	private Image getNavalImageCorvetteBody() {
//		if (navalImageCorvetteBody == null) {
//			navalImageCorvetteBody = getImage("/net/sf/robocode/ui/images/naval/corvette-body.png");
//		}
//		return navalImageCorvetteBody;
//	}
//	private Image getNavalImageCarrierBody() {
//		if (navalImageCarrierBody == null) {
//			navalImageCarrierBody = getImage("/net/sf/robocode/ui/images/naval/carrier-body.png");
//		}
//		return navalImageCarrierBody;
//	}

	/**
	 * Gets the gun image
	 * Loads from disk if necessary.
	 *
	 * @return the gun image
	 */
	private Image getGunImage() {
		if (gunImage == null) {
			gunImage = getImage("/net/sf/robocode/ui/images/turret.png");
		}
		return gunImage;
	}
//	private Image getNavalImageMissile() {
//		if (navalImageGun == null) {
//			navalImageGun = getImage("/net/sf/robocode/ui/images/naval/turret.png");
//		}
//		return navalImageGun;
//	}
	private Image getNavalImageGun(CannonType type){
		switch (type){
			case DOUBLE_BARREL:
				if (navalImageDoubleGun == null) {
					navalImageDoubleGun = getImage("/net/sf/robocode/ui/images/naval/turret-double.png");
				}
				return navalImageDoubleGun;
			case SINGLE_BARREL:
				if (navalImageSingleGun == null) {
					navalImageSingleGun = getImage("/net/sf/robocode/ui/images/naval/turret-single.png");
				}
				return navalImageSingleGun;
		}

		//shouuld not get here
		return null;
	}
//	private Image getNavalImageDoubleGun() {
//		if (navalImageDoubleGun == null) {
//			navalImageDoubleGun = getImage("/net/sf/robocode/ui/images/naval/turret-double.png");
//		}
//		return navalImageDoubleGun;
//	}
//	private Image getNavalImageSingleGun() {
//		if (navalImageSingleGun == null) {
//			navalImageSingleGun = getImage("/net/sf/robocode/ui/images/naval/turret-single.png");
//		}
//		return navalImageSingleGun;
//	}
	private Image getNavalImageMine(){
		if(navalImageMine == null){
			navalImageMine = getImage("/net/sf/robocode/ui/images/naval/Mine.png");
		}
		return navalImageMine;
	}
	private Image getNavalImageMissileComponent() {
		if (navalImageMissileComponent == null) {
			navalImageMissileComponent = getImage("/net/sf/robocode/ui/images/naval/MissileComponent.png");
		}
		return navalImageMissileComponent;
	}
	private Image getNavalImageMissile() {
		if (navalImageMissile == null) {
			navalImageMissile = getImage("/net/sf/robocode/ui/images/naval/Missile.png");
		}
		return navalImageMissile;
	}
	private Image getNavalImageMineComponent(){
		if(navalImageMineComponent == null){
			navalImageMineComponent = getImage("/net/sf/robocode/ui/images/naval/MComponent.png");
		}
		return navalImageMineComponent;
	}

	/**
	 * Gets the radar image
	 * Loads from disk if necessary.
	 *
	 * @return the radar image
	 */
	private Image getRadarImage() {
		if (radarImage == null) {
			radarImage = getImage("/net/sf/robocode/ui/images/radar.png");
		}
		return radarImage;
	}
	private Image getNavalImageLongRadar(){
		if(navalImageLongRadar == null){
			navalImageLongRadar = getImage("/net/sf/robocode/ui/images/naval/radar-long.png");
		}
		return navalImageLongRadar;
	}
	private Image getNavalImageShortRadar(){
		if(navalImageShortRadar == null){
			navalImageShortRadar = getImage("/net/sf/robocode/ui/images/naval/radar-short.png");
		}
		return navalImageShortRadar;
	}

	public RenderImage getColoredBodyRenderImage(Integer color) {
		RenderImage img = robotBodyImageCache.get(color);

		if (img == null) {
			img = new RenderImage(ImageUtil.createColouredRobotImage(getBodyImage(), new Color(color, true)));
			robotBodyImageCache.put(color, img);
		}

		return img;
	}
	public RenderImage getColoredBodyRenderNavalImage(Integer color, ShipType type) {
		RenderImage img;
		switch (type){
			case CRUISER:
				img = navalImageCacheCruiserBody.get(color);

				if (img == null) {
					img = new RenderImage(ImageUtil.createColouredRobotImage(getNavalImageBody(type), new Color(color, true)));
					navalImageCacheCruiserBody.put(color, img);
				}
				return img;
			case CORVETTE:
				img = navalImageCacheCorvetteBody.get(color);

				if (img == null) {
					img = new RenderImage(ImageUtil.createColouredRobotImage(getNavalImageBody(type), new Color(color, true)));
					navalImageCacheCorvetteBody.put(color, img);
				}
				return img;
			case CARRIER:
				img = navalImageCacheCarrierBody.get(color);

				if (img == null) {
					img = new RenderImage(ImageUtil.createColouredRobotImage(getNavalImageBody(type), new Color(color, true)));
					navalImageCacheCarrierBody.put(color, img);
				}
				return img;
		}
		//should not get here
		return null;
	}

	public RenderImage getColoredGunRenderImage(Integer color) {
		RenderImage img = robotGunImageCache.get(color);

		if (img == null) {
			img = new RenderImage(ImageUtil.createColouredRobotImage(getGunImage(), new Color(color, true)));
			robotGunImageCache.put(color, img);
		}
		return img;
	}

	public RenderImage getColoredGunRenderNavalImage(Integer color, CannonType type){
		RenderImage img;
		switch (type){
			case DOUBLE_BARREL:
				img = navalImageCacheDoubleGun.get(color);

				if (img == null) {
					img = new RenderImage(ImageUtil.createColouredRobotImage(getNavalImageGun(type), new Color(color, true)));
					navalImageCacheDoubleGun.put(color, img);
				}
				return img;

			case SINGLE_BARREL:
				img = navalImageCacheSingleGun.get(color);

				if (img == null) {
					img = new RenderImage(ImageUtil.createColouredRobotImage(getNavalImageGun(type), new Color(color, true)));
					navalImageCacheSingleGun.put(color, img);
				}
				return img;
			/**
			 * You can add the image renderer for other guntypes here.
			 */
		}
		//should not get here
		return null;
	}
	public RenderImage getColoredMineRenderNavalImage(Integer color){
		RenderImage img = navalImageCacheMine.get(color);
		
		if(img == null){
			img = new RenderImage(ImageUtil.createColouredRobotImage(getNavalImageMine(), new Color(color,true)));
			navalImageCacheMine.put(color, img);
		}
		return img;
	}
	public RenderImage getColoredMissileComponentRenderNavalImage(Integer color){
		RenderImage img = navalImageCacheMissileComponent.get(color);

		if(img == null){
			img = new RenderImage(getNavalImageMissileComponent());
			navalImageCacheMissileComponent.put(color, img);
		}
		return img;
	}

	public RenderImage getColoredMissileRenderNavalImage(Integer color){
		RenderImage img = navalImageCacheMissile.get(color);

		if(img == null){
			img = new RenderImage(getNavalImageMissile());
			navalImageCacheMissile.put(color, img);
		}
		return img;
	}
	public RenderImage getColoredMineComponentRenderNavalImage(Integer color){
		RenderImage img = navalImageCacheMineComponent.get(color);
		
		if(img == null){
			img = new RenderImage(ImageUtil.createColouredRobotImage(getNavalImageMineComponent(), new Color(color,true)));
			navalImageCacheMineComponent.put(color, img);
		}
		return img;
	}

	public RenderImage getColoredRadarRenderImage(Integer color) {
		RenderImage img = robotRadarImageCache.get(color);

		if (img == null) {
			img = new RenderImage(ImageUtil.createColouredRobotImage(getRadarImage(), new Color(color, true)));
			robotRadarImageCache.put(color, img);
		}
		return img;
	}

	public RenderImage getColoredRadarRenderNavalImage(Integer color, RadarType type) {
		RenderImage img;
		switch (type){
			case LONG_RANGE:
				img = navalImageCacheLongRadar.get(color);

				if (img == null) {
					img = new RenderImage(ImageUtil.createColouredRobotImage(getNavalImageLongRadar(), new Color(color, true)));
					navalImageCacheLongRadar.put(color, img);
				}
				return img;

			case SHORT_RANGE:
				img = navalImageCacheShortRadar.get(color);

				if (img == null) {
					img = new RenderImage(ImageUtil.createColouredRobotImage(getNavalImageShortRadar(), new Color(color, true)));
					navalImageCacheShortRadar.put(color, img);
				}
				return img;
			/**
			 * You can add the image renderer for other radartypes here.
			 */
		}
		//should not get here
		return null;
	}

	/**
	 * Class used for caching rendered robot parts in various colors.
	 *
	 * @author Titus Chen
	 */
	@SuppressWarnings("serial")
	private static class RenderCache<K, V> extends LinkedHashMap<K, V> {

		/* Note about initial capacity:
		 * To avoid rehashing (inefficient though probably unavoidable), initial
		 * capacity must be at least 1 greater than the maximum capacity.
		 * However, initial capacities are set to the smallest power of 2 greater
		 * than or equal to the passed argument, resulting in 512 with this code.
		 * I was not aware of this before, but notice: the current implementation
		 * behaves similarly.  The simple solution would be to set maximum capacity
		 * to 255, but the problem with doing so is that in a battle of 256 robots
		 * of different colors, the net result would end up being real-time
		 * rendering due to the nature of access ordering.  However, 256 robot
		 * battles are rarely fought.
		 */
		private static final int INITIAL_CAPACITY = MAX_NUM_COLORS + 1;

		private static final float LOAD_FACTOR = 1;

		public RenderCache() {

			/* The "true" parameter needed for access-order:
			 * when cache fills, the least recently accessed entry is removed
			 */
			super(INITIAL_CAPACITY, LOAD_FACTOR, true);
		}

		@Override
		protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
			return size() > MAX_NUM_COLORS;
		}
	}
}
