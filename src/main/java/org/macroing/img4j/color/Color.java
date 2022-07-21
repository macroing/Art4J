/**
 * Copyright 2022 J&#246;rgen Lundgren
 * 
 * This file is part of org.macroing.img4j.
 * 
 * org.macroing.img4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * org.macroing.img4j is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with org.macroing.img4j. If not, see <http://www.gnu.org/licenses/>.
 */
package org.macroing.img4j.color;

import org.macroing.img4j.utility.Doubles;
import org.macroing.img4j.utility.Floats;

/**
 * This {@code Color} class consists exclusively of static methods that operates on colors that are defined by primitive types such as {@code double} and {@code int}.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class Color {
	/**
	 * An {@code int} that represents the color black in packed form.
	 * <p>
	 * The color is equivalent to {@code Color.packRGB(0, 0, 0)}.
	 */
	public static final int BLACK = packRGB(0, 0, 0);
	
	/**
	 * An {@code int} that represents the color blue in packed form.
	 * <p>
	 * The color is equivalent to {@code Color.packRGB(0, 0, 255)}.
	 */
	public static final int BLUE = packRGB(0, 0, 255);
	
	/**
	 * An {@code int} that represents the color cyan in packed form.
	 * <p>
	 * The color is equivalent to {@code Color.packRGB(0, 255, 255)}.
	 */
	public static final int CYAN = packRGB(0, 255, 255);
	
	/**
	 * An {@code int} that represents the color gray in packed form.
	 * <p>
	 * The color is equivalent to {@code Color.packRGB(128, 128, 128)}.
	 */
	public static final int GRAY = packRGB(128, 128, 128);
	
	/**
	 * An {@code int} that represents the color green in packed form.
	 * <p>
	 * The color is equivalent to {@code Color.packRGB(0, 255, 0)}.
	 */
	public static final int GREEN = packRGB(0, 255, 0);
	
	/**
	 * An {@code int} that represents the color magenta in packed form.
	 * <p>
	 * The color is equivalent to {@code Color.packRGB(255, 0, 255)}.
	 */
	public static final int MAGENTA = packRGB(255, 0, 255);
	
	/**
	 * An {@code int} that represents the color red in packed form.
	 * <p>
	 * The color is equivalent to {@code Color.packRGB(255, 0, 0)}.
	 */
	public static final int RED = packRGB(255, 0, 0);
	
	/**
	 * An {@code int} that represents the color transparent in packed form.
	 * <p>
	 * The color is equivalent to {@code Color.packRGBA(0, 0, 0, 0)}.
	 */
	public static final int TRANSPARENT = packRGBA(0, 0, 0, 0);
	
	/**
	 * An {@code int} that represents the color white in packed form.
	 * <p>
	 * The color is equivalent to {@code Color.packRGB(255, 255, 255)}.
	 */
	public static final int WHITE = packRGB(255, 255, 255);
	
	/**
	 * An {@code int} that represents the color yellow in packed form.
	 * <p>
	 * The color is equivalent to {@code Color.packRGB(255, 255, 0)}.
	 */
	public static final int YELLOW = packRGB(255, 255, 0);
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private Color() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Blends the component values of {@code colorARGBLHS} and {@code colorARGBRHS}.
	 * <p>
	 * Returns an {@code int} that contains the color in packed form.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Color.blend(colorARGBLHS, colorARGBRHS, t, t, t, t);
	 * }
	 * </pre>
	 * 
	 * @param colorARGBLHS an {@code int} that contains packed A-, R-, G- and B-components
	 * @param colorARGBRHS an {@code int} that contains packed A-, R-, G- and B-components
	 * @param t the factor to use for all components in the blending process
	 * @return an {@code int} that contains the color in packed form
	 */
	public static int blend(final int colorARGBLHS, final int colorARGBRHS, final double t) {
		return blend(colorARGBLHS, colorARGBRHS, t, t, t, t);
	}
	
	/**
	 * Blends the component values of {@code colorARGBLHS} and {@code colorARGBRHS}.
	 * <p>
	 * Returns an {@code int} that contains the color in packed form.
	 * 
	 * @param colorARGBLHS an {@code int} that contains packed A-, R-, G- and B-components
	 * @param colorARGBRHS an {@code int} that contains packed A-, R-, G- and B-components
	 * @param tR the factor to use for the R-component in the blending process
	 * @param tG the factor to use for the G-component in the blending process
	 * @param tB the factor to use for the B-component in the blending process
	 * @param tA the factor to use for the A-component in the blending process
	 * @return an {@code int} that contains the color in packed form
	 */
	public static int blend(final int colorARGBLHS, final int colorARGBRHS, final double tR, final double tG, final double tB, final double tA) {
		final int r = Utilities.convertComponentFromDoubleToInt(Doubles.lerp(Color4D.fromIntARGBToDoubleR(colorARGBLHS), Color4D.fromIntARGBToDoubleR(colorARGBRHS), tR));
		final int g = Utilities.convertComponentFromDoubleToInt(Doubles.lerp(Color4D.fromIntARGBToDoubleG(colorARGBLHS), Color4D.fromIntARGBToDoubleG(colorARGBRHS), tG));
		final int b = Utilities.convertComponentFromDoubleToInt(Doubles.lerp(Color4D.fromIntARGBToDoubleB(colorARGBLHS), Color4D.fromIntARGBToDoubleB(colorARGBRHS), tB));
		final int a = Utilities.convertComponentFromDoubleToInt(Doubles.lerp(Color4D.fromIntARGBToDoubleA(colorARGBLHS), Color4D.fromIntARGBToDoubleA(colorARGBRHS), tA));
		
		return packRGBA(r, g, b, a);
	}
	
	/**
	 * Blends the component values of {@code colorARGBLHS} and {@code colorARGBRHS}.
	 * <p>
	 * Returns an {@code int} that contains the color in packed form.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Color.blend(colorARGBLHS, colorARGBRHS, t, t, t, t);
	 * }
	 * </pre>
	 * 
	 * @param colorARGBLHS an {@code int} that contains packed A-, R-, G- and B-components
	 * @param colorARGBRHS an {@code int} that contains packed A-, R-, G- and B-components
	 * @param t the factor to use for all components in the blending process
	 * @return an {@code int} that contains the color in packed form
	 */
	public static int blend(final int colorARGBLHS, final int colorARGBRHS, final float t) {
		return blend(colorARGBLHS, colorARGBRHS, t, t, t, t);
	}
	
	/**
	 * Blends the component values of {@code colorARGBLHS} and {@code colorARGBRHS}.
	 * <p>
	 * Returns an {@code int} that contains the color in packed form.
	 * 
	 * @param colorARGBLHS an {@code int} that contains packed A-, R-, G- and B-components
	 * @param colorARGBRHS an {@code int} that contains packed A-, R-, G- and B-components
	 * @param tR the factor to use for the R-component in the blending process
	 * @param tG the factor to use for the G-component in the blending process
	 * @param tB the factor to use for the B-component in the blending process
	 * @param tA the factor to use for the A-component in the blending process
	 * @return an {@code int} that contains the color in packed form
	 */
	public static int blend(final int colorARGBLHS, final int colorARGBRHS, final float tR, final float tG, final float tB, final float tA) {
		final int r = Utilities.convertComponentFromFloatToInt(Floats.lerp(Color4F.fromIntARGBToFloatR(colorARGBLHS), Color4F.fromIntARGBToFloatR(colorARGBRHS), tR));
		final int g = Utilities.convertComponentFromFloatToInt(Floats.lerp(Color4F.fromIntARGBToFloatG(colorARGBLHS), Color4F.fromIntARGBToFloatG(colorARGBRHS), tG));
		final int b = Utilities.convertComponentFromFloatToInt(Floats.lerp(Color4F.fromIntARGBToFloatB(colorARGBLHS), Color4F.fromIntARGBToFloatB(colorARGBRHS), tB));
		final int a = Utilities.convertComponentFromFloatToInt(Floats.lerp(Color4F.fromIntARGBToFloatA(colorARGBLHS), Color4F.fromIntARGBToFloatA(colorARGBRHS), tA));
		
		return packRGBA(r, g, b, a);
	}
	
	/**
	 * Blends the components values of {@code colorARGB11}, {@code colorARGB12}, {@code colorARGB21} and {@code colorARGB22}.
	 * <p>
	 * Returns an {@code int} that contains the color in packed form.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Color.blend(Color.blend(colorARGB11, colorARGB12, tX), Color.blend(colorARGB21, colorARGB22, tX), tY);
	 * }
	 * </pre>
	 * 
	 * @param colorARGB11 an {@code int} that contains packed A-, R-, G- and B-components
	 * @param colorARGB12 an {@code int} that contains packed A-, R-, G- and B-components
	 * @param colorARGB21 an {@code int} that contains packed A-, R-, G- and B-components
	 * @param colorARGB22 an {@code int} that contains packed A-, R-, G- and B-components
	 * @param tX the factor to use for all components in the first two blend operations
	 * @param tY the factor to use for all components in the third blend operation
	 * @return an {@code int} that contains the color in packed form
	 */
	public static int blend(final int colorARGB11, final int colorARGB12, final int colorARGB21, final int colorARGB22, final double tX, final double tY) {
		return blend(blend(colorARGB11, colorARGB12, tX), blend(colorARGB21, colorARGB22, tX), tY);
	}
	
	/**
	 * Blends the components values of {@code colorARGB11}, {@code colorARGB12}, {@code colorARGB21} and {@code colorARGB22}.
	 * <p>
	 * Returns an {@code int} that contains the color in packed form.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Color.blend(Color.blend(colorARGB11, colorARGB12, tX), Color.blend(colorARGB21, colorARGB22, tX), tY);
	 * }
	 * </pre>
	 * 
	 * @param colorARGB11 an {@code int} that contains packed A-, R-, G- and B-components
	 * @param colorARGB12 an {@code int} that contains packed A-, R-, G- and B-components
	 * @param colorARGB21 an {@code int} that contains packed A-, R-, G- and B-components
	 * @param colorARGB22 an {@code int} that contains packed A-, R-, G- and B-components
	 * @param tX the factor to use for all components in the first two blend operations
	 * @param tY the factor to use for all components in the third blend operation
	 * @return an {@code int} that contains the color in packed form
	 */
	public static int blend(final int colorARGB11, final int colorARGB12, final int colorARGB21, final int colorARGB22, final float tX, final float tY) {
		return blend(blend(colorARGB11, colorARGB12, tX), blend(colorARGB21, colorARGB22, tX), tY);
	}
	
	/**
	 * Returns an {@code int} that contains {@code r}, {@code g}, {@code b} and {@code 1.0D} in packed form.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Color.packRGBA(r, g, b, 1.0D);
	 * }
	 * </pre>
	 * 
	 * @param r the value of the R-component
	 * @param g the value of the G-component
	 * @param b the value of the B-component
	 * @return an {@code int} that contains {@code r}, {@code g}, {@code b} and {@code 1.0D} in packed form
	 */
	public static int packRGB(final double r, final double g, final double b) {
		return packRGBA(r, g, b, 1.0D);
	}
	
	/**
	 * Returns an {@code int} that contains {@code r}, {@code g}, {@code b} and {@code 1.0F} in packed form.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Color.packRGBA(r, g, b, 1.0F);
	 * }
	 * </pre>
	 * 
	 * @param r the value of the R-component
	 * @param g the value of the G-component
	 * @param b the value of the B-component
	 * @return an {@code int} that contains {@code r}, {@code g}, {@code b} and {@code 1.0F} in packed form
	 */
	public static int packRGB(final float r, final float g, final float b) {
		return packRGBA(r, g, b, 1.0F);
	}
	
	/**
	 * Returns an {@code int} that contains {@code r}, {@code g}, {@code b} and {@code 255} in packed form.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Color.packRGBA(r, g, b, 255);
	 * }
	 * </pre>
	 * 
	 * @param r the value of the R-component
	 * @param g the value of the G-component
	 * @param b the value of the B-component
	 * @return an {@code int} that contains {@code r}, {@code g}, {@code b} and {@code 255} in packed form
	 */
	public static int packRGB(final int r, final int g, final int b) {
		return packRGBA(r, g, b, 255);
	}
	
	/**
	 * Returns an {@code int} that contains {@code r}, {@code g}, {@code b} and {@code a} in packed form.
	 * 
	 * @param r the value of the R-component
	 * @param g the value of the G-component
	 * @param b the value of the B-component
	 * @param a the value of the A-component
	 * @return an {@code int} that contains {@code r}, {@code g}, {@code b} and {@code a} in packed form
	 */
	public static int packRGBA(final double r, final double g, final double b, final double a) {
		return packRGBA(Utilities.convertComponentFromDoubleToInt(r), Utilities.convertComponentFromDoubleToInt(g), Utilities.convertComponentFromDoubleToInt(b), Utilities.convertComponentFromDoubleToInt(a));
	}
	
	/**
	 * Returns an {@code int} that contains {@code r}, {@code g}, {@code b} and {@code a} in packed form.
	 * 
	 * @param r the value of the R-component
	 * @param g the value of the G-component
	 * @param b the value of the B-component
	 * @param a the value of the A-component
	 * @return an {@code int} that contains {@code r}, {@code g}, {@code b} and {@code a} in packed form
	 */
	public static int packRGBA(final float r, final float g, final float b, final float a) {
		return packRGBA(Utilities.convertComponentFromFloatToInt(r), Utilities.convertComponentFromFloatToInt(g), Utilities.convertComponentFromFloatToInt(b), Utilities.convertComponentFromFloatToInt(a));
	}
	
	/**
	 * Returns an {@code int} that contains {@code r}, {@code g}, {@code b} and {@code a} in packed form.
	 * 
	 * @param r the value of the R-component
	 * @param g the value of the G-component
	 * @param b the value of the B-component
	 * @param a the value of the A-component
	 * @return an {@code int} that contains {@code r}, {@code g}, {@code b} and {@code a} in packed form
	 */
	public static int packRGBA(final int r, final int g, final int b, final int a) {
		final int packedR = ((r & 0xFF) << Utilities.COLOR_A_R_G_B_SHIFT_R);
		final int packedG = ((g & 0xFF) << Utilities.COLOR_A_R_G_B_SHIFT_G);
		final int packedB = ((b & 0xFF) << Utilities.COLOR_A_R_G_B_SHIFT_B);
		final int packedA = ((a & 0xFF) << Utilities.COLOR_A_R_G_B_SHIFT_A);
		
		return packedR | packedG | packedB | packedA;
	}
	
	/**
	 * Converts {@code colorARGB} to its Sepia-representation.
	 * <p>
	 * Returns an {@code int} that contains the color in packed form.
	 * 
	 * @param colorARGB an {@code int} that contains packed A-, R-, G- and B-components
	 * @return an {@code int} that contains the color in packed form
	 */
	public static int sepia(final int colorARGB) {
		final double colorAR = Color4D.fromIntARGBToDoubleR(colorARGB);
		final double colorAG = Color4D.fromIntARGBToDoubleG(colorARGB);
		final double colorAB = Color4D.fromIntARGBToDoubleB(colorARGB);
		final double colorAA = Color4D.fromIntARGBToDoubleA(colorARGB);
		
		final double colorBR = colorAR * 0.393D + colorAG * 0.769D + colorAB * 0.189D;
		final double colorBG = colorAR * 0.349D + colorAG * 0.686D + colorAB * 0.168D;
		final double colorBB = colorAR * 0.272D + colorAG * 0.534D + colorAB * 0.131D;
		final double colorBA = colorAA;
		
		return packRGBA(colorBR, colorBG, colorBB, colorBA);
	}
}