//------------------------------------------------------------------------------------------------//
//                                                                                                //
//                                           P a r a m s                                          //
//                                                                                                //
//------------------------------------------------------------------------------------------------//
// <editor-fold defaultstate="collapsed" desc="hdr">
//
//  Copyright © Audiveris 2023. All rights reserved.
//
//  This program is free software: you can redistribute it and/or modify it under the terms of the
//  GNU Affero General Public License as published by the Free Software Foundation, either version
//  3 of the License, or (at your option) any later version.
//
//  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
//  without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
//  See the GNU Affero General Public License for more details.
//
//  You should have received a copy of the GNU Affero General Public License along with this
//  program.  If not, see <http://www.gnu.org/licenses/>.
//------------------------------------------------------------------------------------------------//
// </editor-fold>
package org.audiveris.omr.sheet;

import org.audiveris.omr.text.Language;
import org.audiveris.omr.ui.symbol.MusicFamily;
import org.audiveris.omr.ui.symbol.MusicFont;
import org.audiveris.omr.ui.symbol.TextFamily;
import org.audiveris.omr.ui.symbol.TextFont;
import org.audiveris.omr.util.param.IntegerParam;
import org.audiveris.omr.util.param.StringParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.annotation.XmlElement;

/**
 * Class <code>Params</code> is the base structure of editable parameters for the Book and
 * SheetStub classes.
 *
 * @param <P> parent type: Either Object or Book or SheetStub
 * @param <S> scope type: Either Book or SheetStub
 * @author Hervé Bitteur
 */
public abstract class Params<P, S>
        implements Cloneable
{
    //~ Static fields/initializers -----------------------------------------------------------------

    private static final Logger logger = LoggerFactory.getLogger(Params.class);

    //~ Instance fields ----------------------------------------------------------------------------

    /** Specification of the MusicFont family to use. */
    @XmlElement(name = "music-font")
    public MusicFamily.MyParam musicFamily;

    /** Specification of the TextFont family to use. */
    @XmlElement(name = "text-font")
    public TextFamily.MyParam textFamily;

    /** Specification of the input quality to use. */
    @XmlElement(name = "input-quality")
    public InputQualityParam inputQuality;

    /** Specification of interline in pixels. */
    @XmlElement(name = "interline")
    public IntegerParam interlineSpecification;

    /** Specification of barline height, in interlines, for 1-line staves. */
    @XmlElement(name = "barline-height")
    public BarlineHeight.MyParam barlineSpecification;

    /** Specification of beam thickness, in pixels. */
    @XmlElement(name = "beam-thickness")
    public IntegerParam beamSpecification;

    /**
     * This string specifies the dominant language(s).
     * <p>
     * For example, <code>eng+ita</code> specification will ask OCR to use English and Italian
     * dictionaries and only those.
     */
    @XmlElement(name = "ocr-languages")
    public StringParam ocrLanguages;

    /** The set of specific processing switches. */
    @XmlElement(name = "processing")
    public ProcessingSwitches switches;

    //~ Constructors -------------------------------------------------------------------------------

    // Needed for JAXB
    @SuppressWarnings("unused")
    protected Params ()
    {

    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * For any missing Param in this structure, allocate the proper Param.
     */
    public final void completeParams ()
    {
        if (musicFamily == null)
            musicFamily = new MusicFamily.MyParam(null);

        if (textFamily == null)
            textFamily = new TextFamily.MyParam(null);

        if (inputQuality == null)
            inputQuality = new InputQualityParam(null);

        if (interlineSpecification == null)
            interlineSpecification = new IntegerParam(null);

        if (barlineSpecification == null)
            barlineSpecification = new BarlineHeight.MyParam(null);

        if (beamSpecification == null)
            beamSpecification = new IntegerParam(null);

        if (ocrLanguages == null)
            ocrLanguages = new StringParam(null);

        if (switches == null)
            switches = new ProcessingSwitches(ProcessingSwitches.getDefaultSwitches(), null);
    }

    /** Clone the structure. */
    public abstract Params duplicate ();

    private boolean isEmpty ()
    {
        return musicFamily == null //
                && textFamily == null //
                && inputQuality == null //
                && interlineSpecification == null //
                && barlineSpecification == null //
                && beamSpecification == null //
                && ocrLanguages == null //
                && switches == null;
    }

    public boolean prune ()
    {
        if ((musicFamily != null) && !musicFamily.isSpecific()) {
            musicFamily = null;
        }

        if ((textFamily != null) && !textFamily.isSpecific()) {
            textFamily = null;
        }

        if ((inputQuality != null) && !inputQuality.isSpecific()) {
            inputQuality = null;
        }

        if ((ocrLanguages != null) && !ocrLanguages.isSpecific()) {
            ocrLanguages = null;
        }

        // A O value means no specific value
        if ((interlineSpecification != null) //
                && ((interlineSpecification.getSpecific() == null) //
                        || (interlineSpecification.getSpecific() == 0))) {
            interlineSpecification = null;
        }

        if ((barlineSpecification != null) && !barlineSpecification.isSpecific()) {
            barlineSpecification = null;
        }

        // A O value means no specific value
        if ((beamSpecification != null) //
                && ((beamSpecification.getSpecific() == null) //
                        || (beamSpecification.getSpecific() == 0))) {
            beamSpecification = null;
        }

        if ((switches != null) && switches.isEmpty()) {
            switches = null;
        }

        return isEmpty();
    }

    public abstract void setParents (P parent);

    public final void setScope (S scope)
    {
        musicFamily.setScope(scope);
        textFamily.setScope(scope);
        inputQuality.setScope(scope);
        interlineSpecification.setScope(scope);
        barlineSpecification.setScope(scope);
        beamSpecification.setScope(scope);
        ocrLanguages.setScope(scope);
        switches.setScope(scope);
    }

    //~ Inner Classes ------------------------------------------------------------------------------

    //------------//
    // BookParams //
    //------------//
    /**
     * Parameters structure for a Book.
     */
    public static class BookParams
            extends Params<Object, Book>
    {
        // Needed for JAXB
        @SuppressWarnings("unused")
        public BookParams ()
        {

        }

        public BookParams (Book unused)
        {
            completeParams();
            setParents(null);
        }

        @Override
        public BookParams duplicate ()
        {
            try {
                return (BookParams) super.clone();
            } catch (CloneNotSupportedException ex) {
                return null; // Should never happen
            }
        }

        @Override
        public final void setParents (Object ignored)
        {
            musicFamily.setParent(MusicFont.defaultMusicParam);
            textFamily.setParent(TextFont.defaultTextParam);
            inputQuality.setParent(Profiles.defaultQualityParam);
            interlineSpecification.setParent(Scale.defaultInterlineSpecification);
            barlineSpecification.setParent(BarlineHeight.defaultParam);
            beamSpecification.setParent(Scale.defaultBeamSpecification);
            ocrLanguages.setParent(Language.ocrDefaultLanguages);
        }
    }

    //-------------//
    // SheetParams //
    //-------------//
    /**
     * Parameters structure for a SheetStub.
     */
    public static class SheetParams
            extends Params<Book, SheetStub>
    {
        public SheetParams ()
        {

        }

        public SheetParams (Book ignored)
        {
            completeParams();
        }

        @Override
        public SheetParams duplicate ()
        {
            try {
                return (SheetParams) super.clone();
            } catch (CloneNotSupportedException ex) {
                return null; // Should never happen
            }
        }

        @Override
        public final void setParents (Book book)
        {
            musicFamily.setParent(book.getMusicFamilyParam());
            textFamily.setParent(book.getTextFamilyParam());
            inputQuality.setParent(book.getInputQualityParam());
            ocrLanguages.setParent(book.getOcrLanguagesParam());
            interlineSpecification.setParent(book.getInterlineSpecificationParam());
            barlineSpecification.setParent(book.getBarlineHeightParam());
            beamSpecification.setParent(book.getBeamSpecificationParam());
        }
    }
}
