package org.parafia.util.displaytag;

import java.awt.Color;
import java.io.IOException;
import java.util.Iterator;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.displaytag.decorator.TableDecorator;
import org.displaytag.exception.DecoratorException;
import org.displaytag.exception.ObjectLookupException;
import org.displaytag.model.Column;
import org.displaytag.model.HeaderCell;
import org.displaytag.model.TableModel;
import org.displaytag.render.TableWriterAdapter;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Cell;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.BaseFont;


/**
 * A table writer that formats table as and writes it to an iText document.
 * @author Jorge L. Barroso
 * @version $Id$
 * @see org.displaytag.render.TableWriterTemplate
 */
public class ITextTableWriter extends TableWriterAdapter
{
	private final Logger log = Logger.getLogger(getClass());
    /**
     * iText representation of the table.
     */
    private Table table;

    /**
     * iText document to which the table is written.
     */
    private Document document;

    /**
     * The default font used in the document.
     */
    private Font defaultFont;

    /**
     * This table writer uses an iText table and document to do its work.
     * @param table iText representation of the table.
     * @param document iText document to which the table is written.
     */
    public ITextTableWriter(Table table, Document document)
    {
        this.table = table;
        this.document = document;
    }

    /**
     * Initialize the main info holder table, like the appropriate number of columns.
     * @param model The table being represented as iText.
     * @see org.displaytag.render.TableWriterTemplate#writeTableOpener(org.displaytag.model.TableModel)
     */
    protected void writeTableOpener(TableModel model)
    {
        this.table.setDefaultVerticalAlignment(Element.ALIGN_TOP);
        this.table.setCellsFitPage(true);
        this.table.setWidth(100);
        this.table.setPadding(2);
        this.table.setSpacing(0);
        this.table.setBorder(Rectangle.NO_BORDER);
        this.defaultFont = this.getTableFont();
    }

    /**
     * Obtain the font used to render text in the table; Meant to be overriden if a different font is desired.
     * @return The font used to render text in the table.
     */
    protected Font getTableFont()
    {
        return getArial(12, Font.NORMAL);
    }

    /**
     * Write the table's caption to a iText document.
     * @see org.displaytag.render.TableWriterTemplate#writeCaption(org.displaytag.model.TableModel)
     */
    protected void writeCaption(TableModel model) throws Exception
    {
        this.decorateCaption(model);
    }

    /**
     * Writes the table caption according to a set style.
     * @param model The table model containing the caption.
     * @throws DocumentException If an error occurrs while decorating the caption.
     */
    private void decorateCaption(TableModel model) throws DocumentException
    {
        Paragraph caption = new Paragraph(new Chunk(model.getCaption(), this.getCaptionFont()));
        caption.setAlignment(this.getCaptionHorizontalAlignment());
        this.document.add(caption);
    }

    /**
     * Obtain the caption font; Meant to be overriden if a different style is desired.
     * @return The caption font.
     */
    protected Font getCaptionFont() {
    	return getArial(15, Font.BOLD);
    }

    /**
     * Obtain the caption horizontal alignment; Meant to be overriden if a different style is desired.
     * @return The caption horizontal alignment.
     */
    protected int getCaptionHorizontalAlignment()
    {
        return Element.ALIGN_CENTER;
    }

    /**
     * Write the table's header columns to an iText document.
     * @see org.displaytag.render.TableWriterTemplate#writeTableHeader(org.displaytag.model.TableModel)
     * @throws BadElementException if an error occurs while writing header.
     */
    protected void writeTableHeader(TableModel model) throws BadElementException
    {
        Iterator iterator = model.getHeaderCellList().iterator();

        float[] widths = new float[model.getNumberOfColumns()];
        for (int i = 0; iterator.hasNext(); i++)
        {
            HeaderCell headerCell = (HeaderCell) iterator.next();
            
            widths[i] = this.getCellWidth(headerCell);

            String columnHeader = headerCell.getTitle();

            if (columnHeader == null)
            {
                columnHeader = StringUtils.capitalize(headerCell.getBeanPropertyName());
            }
            
            Cell hdrCell = this.getHeaderCell(columnHeader);
            this.table.addCell(hdrCell);
        }
        this.table.setWidths(widths);
        this.table.endHeaders();
    }

    /**
     * Returns the maximum size of all values in this column.
     * @param headerCell Header cell for this column.
     * @return The maximum size of all values in this column.
     */
    private float getCellWidth(HeaderCell headerCell)
    {
        /*int maxWidth = headerCell.getMaxLength();
        return (maxWidth > 0) ? maxWidth : headerCell.getTitle().length();*/
    	return 30;
    }

    /**
     * @see org.displaytag.render.TableWriterTemplate#writePostBodyFooter(org.displaytag.model.TableModel)
     * @throws DocumentException if an error occurs while writing post-body footer.
     */
    protected void writePostBodyFooter(TableModel model) throws DocumentException
    {
        Chunk cellContent = new Chunk(model.getFooter(), this.getFooterFont());
		        
        Cell cell = new Cell(cellContent);
        cell.setGrayFill(30f);
        cell.setLeading(12);
        cell.setBackgroundColor(this.getFooterBackgroundColor());
        cell.setHorizontalAlignment(this.getFooterHorizontalAlignment());
        cell.setColspan(model.getNumberOfColumns());
        table.addCell(cell);
    }

    /**
     * Obtain the footer background color; Meant to be overriden if a different style is desired.
     * @return The footer background color.
     */
    protected Color getFooterBackgroundColor()
    {
        return new Color(0xce, 0xcf, 0xce);
    }

    /**
     * Obtain the footer horizontal alignment; Meant to be overriden if a different style is desired.
     * @return The footer horizontal alignment.
     */
    protected int getFooterHorizontalAlignment()
    {
        return Element.ALIGN_LEFT;
    }

    /**
     * Set the font style used to render the header text; Meant to be overridden if a different header style is desired.
     * @param cellContent The header content whose font will be modified.
     */
    protected void setFooterFontStyle(Chunk cellContent)
    {
        //this.setBoldStyle(cellContent, this.getFooterFontColor());
    	cellContent.setFont(FontFactory.getFont(getFooterFont().getFamilyname(), getFooterFont().size()));
    }

    /**
     * Obtain the footer font color; Meant to be overriden if a different style is desired.
     * @return The footer font color.
     */
    protected Color getFooterFontColor()
    {
        return new Color(0x00, 0x00, 0x00);
    }

    /**
     * Obtain the footer font; Meant to be overriden if a different style is desired.
     * @return The footer font.
     */
    protected Font getFooterFont() {
        return getArial(12, Font.NORMAL);
    }
    
    protected Font getArial(int size, int fontWeight) {
	    BaseFont arial = null;
		try {
			Configuration config = new PropertiesConfiguration("config.properties");
			log.debug("Bierzemy font z " + config.getString("font.ttf.location"));
			arial = BaseFont.createFont(config.getString("font.ttf.location"),  BaseFont.IDENTITY_H, BaseFont.CACHED);
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ConfigurationException e) {
			e.printStackTrace();
			try {
				arial = BaseFont.createFont("Arial",  BaseFont.IDENTITY_H, BaseFont.CACHED);
			} catch (DocumentException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	    com.lowagie.text.Font timesFont = new com.lowagie.text.Font(arial , size, fontWeight);
	    return timesFont;
    }

    /**
     * Decorators that help render the table to an iText document must implement ItextDecorator.
     * @see org.displaytag.render.TableWriterTemplate#writeDecoratedRowStart(org.displaytag.model.TableModel)
     */
    protected void writeDecoratedRowStart(TableModel model)
    {
        TableDecorator decorator =  model.getTableDecorator();
        if (decorator instanceof ItextDecorator)
        {
        	ItextDecorator idecorator = (ItextDecorator) decorator;
            idecorator.setTable(this.table);
            idecorator.setFont(this.defaultFont);
        }
        decorator.startRow();
    }

    /**
     * @see org.displaytag.render.TableWriterTemplate#writeDecoratedRowFinish(org.displaytag.model.TableModel)
     */
    protected void writeDecoratedRowFinish(TableModel model) throws Exception
    {
        model.getTableDecorator().finishRow();
    }

    /**
     * Write a column's opening structure to an iText document.
     * @see org.displaytag.render.TableWriterTemplate#writeColumnOpener(org.displaytag.model.Column)
     */
    protected void writeColumnOpener(Column column) throws ObjectLookupException, DecoratorException
    {
        column.initialize(); // has side effect, setting its stringValue, which affects grouping logic.
    }

    /**
     * Write a column's value to a iText document.
     * @see org.displaytag.render.TableWriterTemplate#writeColumnValue(Object,org.displaytag.model.Column)
     */
    protected void writeColumnValue(Object value, Column column) throws BadElementException
    {
        this.table.addCell(getCell(value));
    }

    /**
     * @see org.displaytag.render.TableWriterTemplate#writeDecoratedTableFinish(org.displaytag.model.TableModel)
     */
    protected void writeDecoratedTableFinish(TableModel model)
    {
        model.getTableDecorator().finish();
    }

    /**
     * Returns a formatted cell for the given value.
     * @param value cell value
     * @return Cell
     * @throws BadElementException if errors occurs while generating content.
     */
    private Cell getCell(Object value) throws BadElementException
    {
        Cell cell = new Cell(new Chunk(StringUtils.trimToEmpty(ObjectUtils.toString(value)), this.defaultFont));
        cell.setVerticalAlignment(Element.ALIGN_TOP);
        cell.setLeading(12);
        return cell;
    }

    /**
     * Obtain a header cell.
     * @param value Cell content.
     * @return A header cell with the given content.
     * @throws BadElementException if errors occurs while generating content.
     */
    private Cell getHeaderCell(String value) throws BadElementException
    {
        Chunk cellContent = new Chunk(value, this.getHeaderFont());
        //setHeaderFontStyle(cellContent);
        Cell cell = new Cell(cellContent);
        cell.setLeading(12);
        cell.setHeader(true);
        cell.setHorizontalAlignment(this.getHeaderHorizontalAlignment());
        cell.setBackgroundColor(this.getHeaderBackgroundColor());
        return cell;
    }

    /**
     * Obtain the font used to render the header text; Meant to be overridden if a different header font is desired.
     * @return The font used to render the header text.
     */
    protected Font getHeaderFont()
    {
        return getArial(12, Font.BOLD);
    }

    /**
     * Obtain the background color used to render the header; Meant to be overridden if a different header background
     * color is desired.
     * @return The backgrounc color used to render the header.
     */
    protected Color getHeaderBackgroundColor()
    {
        return new Color(0xee, 0xee, 0xee);
    }

    /**
     * Set the font style used to render the header text; Meant to be overridden if a different header style is desired.
     * @param cellContent The header content whose font will be modified.
     */
    protected void setHeaderFontStyle(Chunk cellContent)
    {
        setBoldStyle(cellContent, this.getHeaderFontColor());
    }

    /**
     * Set the font color used to render the header text; Meant to be overridden if a different header style is desired.
     * @return The font color used to render the header text.
     */
    protected Color getHeaderFontColor()
    {
        return new Color(0x00, 0x00, 0x00);
    }

    /**
     * Obtain the horizontal alignment used to render header text; Meant to be overridden if a different alignment is
     * desired.
     * @return The horizontal alignment used to render header text;
     */
    protected int getHeaderHorizontalAlignment()
    {
        return Element.ALIGN_CENTER;
    }

    /**
     * Makes chunk content bold.
     * @param chunk The chunk whose content is to be rendered bold.
     * @param color The font color desired.
     */
    private void setBoldStyle(Chunk chunk, Color color)
    {
        Font font = chunk.font();
        chunk.setFont(FontFactory.getFont(font.getFamilyname(), font.size(), Font.BOLD, color));
    }

    /**
     * An implementor of this interface decorates tables and columns appearing in iText documents.
     * @author Jorge L. Barroso
     * @version $Revision$ ($Author$)
     */
    public interface ItextDecorator
    {

        /**
         * Set the iText table used to render a table model.
         * @param table The iText table used to render a table model.
         */
        void setTable(Table table);

        /**
         * Set the font used to render a table's content.
         * @param font The font used to render a table's content.
         */
        void setFont(Font font);
    }

}
