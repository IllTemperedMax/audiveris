//-----------------------------------------------------------------------//
//                                                                       //
//                        G l y p h S e c t i o n                        //
//                                                                       //
//  Copyright (C) Herve Bitteur 2000-2005. All rights reserved.          //
//  This software is released under the terms of the GNU General Public  //
//  License. Please contact the author at herve.bitteur@laposte.net      //
//  to report bugs & suggestions.                                        //
//-----------------------------------------------------------------------//
//      $Id$
package omr.glyph;

import omr.lag.Run;

import omr.check.SuccessResult;
import omr.lag.Lag;
import omr.lag.Run;
import omr.lag.Section;

/**
 * Class <code>GlyphSection</code> implements a specific class of section,
 * meant for easy glyph elaboration.
 */
public class GlyphSection
    extends Section<GlyphLag, GlyphSection>
{
    //~ Instance variables ------------------------------------------------

    /**
     * Glyph this section belongs to
     */
    protected Glyph glyph;

    //~ Constructors ------------------------------------------------------

    //--------------//
    // GlyphSection //
    //--------------//
    /**
     * Creates a new GlyphSection.
     */
    public GlyphSection ()
    {
    }

    //--------------//
    // GlyphSection //
    //--------------//
    /**
     * Creates a new GlyphSection.
     */
    public GlyphSection (GlyphLag lag,
                         int firstPos,
                         Run firstRun)
    {
        super(lag, firstPos, firstRun);
    }

    //~ Methods --------------------------------------------------------------

    //----------//
    // getGlyph //
    //----------//
    /**
     * Report the glyph the section belongs to, if any
     *
     * @return the glyph, which may be null
     */
    public Glyph getGlyph ()
    {
        return glyph;
    }

    //----------//
    // setGlyph //
    //----------//
    /**
     * Assign the containing glyph
     *
     * @param glyph the containing glyph
     */
    public void setGlyph (Glyph glyph)
    {
        this.glyph = glyph;
    }

    //-----------//
    // getPrefix //
    //-----------//
    protected String getPrefix ()
    {
        return "GS";
    }

    //----------//
    // isMember //
    //----------//

    /**
     * Checks whether the section is already a member of a glyph, whether this
     * glyph has been successfully recognized or not.
     *
     * @return the result of the test
     */
    public boolean isMember ()
    {
        return glyph != null;
    }

    //---------//
    // isKnown //
    //---------//
    /**
     * Check that the section at hand is a member section, aggregated to a
     * known glyph.
     *
     * @return true if member of a known glyph
     */
    public boolean isKnown ()
    {
        return
            isMember()
            && ((glyph.getResult() != null
                 && glyph.getResult() instanceof SuccessResult)
                || glyph.isKnown())
            ;
    }

    //----------//
    // toString //
    //----------//

    /**
     * A readable description of this entity
     *
     * @return the string
     */
    public String toString ()
    {
        StringBuffer sb = new StringBuffer(256);

        sb.append(super.toString());

        if (glyph != null) {
            sb.append(" glyph=").append(glyph.getId());
        }

        if (this.getClass().getName() == GlyphSection.class.getName()) {
            sb.append("}");
        }

        return sb.toString();
    }
}
