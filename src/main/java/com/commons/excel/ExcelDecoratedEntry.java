package com.commons.excel;

public class ExcelDecoratedEntry {

	public String key;
	public String title;
	private String hint;
	public int width = 3000;
	public Integer columnWidth;
	public String formatPattern;
	private String numberPattern;
	private int rowIndex;
	private int columnIndex;

	private String divisor, dividend;

	private int decimal = 4;

	/**
	 * 如果length>0则缩短显示的文字长度
	 */
	private int length = 0;

	/**
	 * 是否显示title(用于table td)、是否合计
	 */
	private boolean showTitle = false, showTotal = false, showLink = false;

	public ExcelDecoratedEntry(String key, String title, int rowIndex, int columnIndex) {
		this.key = key;
		this.title = title;
		this.rowIndex = rowIndex;
		this.columnIndex = columnIndex;
	}
	public ExcelDecoratedEntry(String key, String title,String hint, int rowIndex, int columnIndex) {
		this.key = key;
		this.title = title;
		this.hint = hint;
		this.rowIndex = rowIndex;
		this.columnIndex = columnIndex;
	}
	public ExcelDecoratedEntry(String key, String title, int rowIndex, int columnIndex, boolean showTotal) {
		this.key = key;
		this.title = title;
		this.rowIndex = rowIndex;
		this.columnIndex = columnIndex;
		this.showTotal = showTotal;
	}

	public ExcelDecoratedEntry(String key, String title, int rowIndex, int columnIndex, String divisor, String dividend) {
		this.key = key;
		this.title = title;
		this.rowIndex = rowIndex;
		this.columnIndex = columnIndex;
		this.divisor = divisor;
		this.dividend = dividend;
	}
	
	public ExcelDecoratedEntry(String key, String title, int rowIndex, int columnIndex, String divisor, String dividend, int decimal) {
		this.key = key;
		this.title = title;
		this.rowIndex = rowIndex;
		this.columnIndex = columnIndex;
		this.divisor = divisor;
		this.dividend = dividend;
		this.decimal = decimal;
	}

	public ExcelDecoratedEntry(String key, String title, int width, String formatPattern, int rowIndex, int columnIndex) {
		this.key = key;
		this.title = title;
		this.width = width;
		this.formatPattern = formatPattern;
		this.rowIndex = rowIndex;
		this.columnIndex = columnIndex;
	}
	public ExcelDecoratedEntry(String key, String title, int rowIndex, int columnIndex, String numberPattern) {
		this.key = key;
		this.title = title;
		this.numberPattern = numberPattern;
		this.rowIndex = rowIndex;
		this.columnIndex = columnIndex;
	}
	public ExcelDecoratedEntry(String key, String title,String hint, int rowIndex, int columnIndex, String numberPattern) {
		this.key = key;
		this.title = title;
		this.hint = hint;
		this.numberPattern = numberPattern;
		this.rowIndex = rowIndex;
		this.columnIndex = columnIndex;
	}
	public ExcelDecoratedEntry(String key, String title, String formatPattern) {
		this.key = key;
		this.title = title;
		this.formatPattern = formatPattern;
	}

	public ExcelDecoratedEntry(String key, String title) {
		this.key = key;
		this.title = title;
	}
	
	public ExcelDecoratedEntry(String key, String title, String divisor, String dividend) {
		this.key = key;
		this.title = title;
		this.divisor = divisor;
		this.dividend = dividend;
	}
	
	public ExcelDecoratedEntry(String key, String title,String hint, String divisor, String dividend,String numberPattern,int decimal) {
		this.key = key;
		this.title = title;
		this.hint = hint;
		this.divisor = divisor;
		this.dividend = dividend;
		this.numberPattern = numberPattern;
		this.decimal = decimal;
	}
	public ExcelDecoratedEntry(String key, String title, String divisor, String dividend,String numberPattern,int decimal) {
		this.key = key;
		this.title = title;
		this.divisor = divisor;
		this.dividend = dividend;
		this.numberPattern = numberPattern;
		this.decimal = decimal;
	}
	public ExcelDecoratedEntry(String key, String title, int width) {
		this.key = key;
		this.title = title;
		this.width = width;
	}
	
	public ExcelDecoratedEntry(String key, String title, Integer columnWidth) {
		this.key = key;
		this.title = title;
		this.columnWidth = columnWidth;
	}

	public ExcelDecoratedEntry(String key, String title, boolean showTotal) {
		this.key = key;
		this.title = title;
		this.showTotal = showTotal;
	}
	public ExcelDecoratedEntry(String key, String title, boolean showLink, boolean showTotal) {
		this.key = key;
		this.title = title;
		this.showLink = showLink;
		this.showTotal = showTotal;
	}
	
	public ExcelDecoratedEntry(String key, String title,String hint, String numberPattern, boolean showTotal) {
		this.key = key;
		this.title = title;
		this.hint = hint;
		this.numberPattern = numberPattern;
		this.showTotal = showTotal;
	}
	public ExcelDecoratedEntry(String key, String title, String numberPattern, int decimal) {
		this.key = key;
		this.title = title;
		this.numberPattern = numberPattern;
		this.decimal = decimal;
	}
	public ExcelDecoratedEntry(String key, String title,String hint, String numberPattern, int decimal) {
		this.key = key;
		this.title = title;
		this.hint = hint;
		this.numberPattern = numberPattern;
		this.decimal = decimal;
	}
	public ExcelDecoratedEntry(String key, String title, int length, boolean showTitle) {
		this.key = key;
		this.title = title;
		this.length = length;
		this.showTitle = showTitle;
	}
	public ExcelDecoratedEntry(String key, String title, int length, boolean showTitle, int width) {
		this.key = key;
		this.title = title;
		this.length = length;
		this.showTitle = showTitle;
		this.width = width;
	}
	public ExcelDecoratedEntry(String key, String title, int length, boolean showTitle, Integer columnWidth) {
		this.key = key;
		this.title = title;
		this.length = length;
		this.showTitle = showTitle;
		this.columnWidth = columnWidth;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getWidth() {
		return (width >= 65536) ? 65500 : width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public String getFormatPattern() {
		return formatPattern;
	}

	public void setFormatPattern(String formatPattern) {
		this.formatPattern = formatPattern;
	}

	public int getRowIndex() {
		return rowIndex;
	}

	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}

	public int getColumnIndex() {
		return columnIndex;
	}

	public void setColumnIndex(int columnIndex) {
		this.columnIndex = columnIndex;
	}

	public int getLength() {
		return length;
	}

	public boolean isShowTitle() {
		return showTitle;
	}

	public boolean isShowTotal() {
		return showTotal;
	}

	public String getDivisor() {
		return divisor;
	}

	public String getDividend() {
		return dividend;
	}

	public int getDecimal() {
		return decimal;
	}

	public void setNumberPattern(String numberPattern) {
		this.numberPattern = numberPattern;
	}

	public String getNumberPattern() {
		return numberPattern;
	}

	public void setHint(String hint) {
		this.hint = hint;
	}

	public String getHint() {
		return hint;
	}
	public Integer getColumnWidth() {
		return columnWidth;
	}
	public void setColumnWidth(Integer columnWidth) {
		this.columnWidth = columnWidth;
	}
	public boolean isShowLink() {
		return showLink;
	}
	public void setShowLink(boolean showLink) {
		this.showLink = showLink;
	}
}