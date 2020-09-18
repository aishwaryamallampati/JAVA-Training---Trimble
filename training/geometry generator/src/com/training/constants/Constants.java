package com.training.constants;

public class Constants {
	public static final double METERS_TO_FEET = 3.28084;
	public static final double METERS_TO_INCHES = 39.3701;
	public static final String POINT_PREFIX = "P";

	/*
	 * Objects that can be created using this program.
	 */
	public enum Object {
		SQUARE, RECTANGLE, CIRCLE, LINE, POINT
	};

	/*
	 * Type of point creation.
	 */
	public enum Creation {
		NEW, RELATIVE
	};

	/*
	 * Choice whether to continue program or not.
	 */
	public enum Choice {
		YES, NO
	};

	/*
	 * Metric for perimeter and area.
	 */
	public enum Metric {
		METER("m"), FEET("ft"), INCH("in");

		private String unit;

		Metric(String unit) {
			this.unit = unit;
		}

		public String getUnit() {
			return unit;
		}
	};

	/*
	 * Attributes for SQUARE/RECTANGLE/CIRCLE/LINE.
	 */
	public enum Attribute {
		PERIMETER, AREA
	};

	/*
	 * Attributes for POINT.
	 */
	public enum PointAttribute {
		ANGLE_DISTANCE("AD"), XY_DISTANCE("DD");

		private String symbol;

		PointAttribute(String symbol) {
			this.symbol = symbol;
		}

		public String getSymbol() {
			return symbol;
		}
	};
}
