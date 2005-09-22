/*
 *  Jajuk
 *  Copyright (C) 2004 bflorat
 *
 *  This program is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU General Public License
 *  as published by the Free Software Foundation; either version 2
 *  of the License, or any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *  $Revision$
 */

package org.jajuk.base;

import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import org.jajuk.i18n.Messages;
import org.jajuk.util.ITechnicalStrings;
import org.jajuk.util.Util;
import org.jajuk.util.log.Log;


/**
 *  A Jajuk property meta information
 * @author     Bertrand Florat
 * @created    14 août 2005
 */
public class PropertyMetaInformation implements ITechnicalStrings{
    
    /**Property name*/
    private String sName;
    /**Is property a custom property?*/
    private boolean bCustom = false;
    /**Is property element of associated item constructor? (and so used in the checksum ID hash)*/
    private boolean bConstructor = false;
    /**Property Type (java.lang.String for ie)*/
    private Class cType;
    /**Format like YYYYmmDD for a date for ie or number of digits for an Integer*/
    Format format;
    /**Default value (null: no default)*/
    Object oDefaultValue;
    /**This property should be displayed to UI?*/
    boolean bShouldBeDisplayed = true;
    /**Editable?*/
    boolean bEditable = true;
    /**Unique?*/
    boolean bUnique = false;
    /**Human Type*/
    private String sHumanType; 
    /**Supported date formats desc*/
    private static ArrayList<String> alDateFormatsDesc = new ArrayList(10);
    /**Supported date formats*/
    private static ArrayList<Format> alDateFormats = new ArrayList(10);
    //Add supported date formats
    static{
        alDateFormatsDesc.add("Date_Default");
        alDateFormats.add(DateFormat.getDateInstance(DateFormat.DEFAULT,Locale.getDefault()));
        alDateFormatsDesc.add("dd/MM/yyyy");
        alDateFormats.add(new SimpleDateFormat("dd/MM/yyyy"));
        alDateFormatsDesc.add("yyyy/MM/dd");
        alDateFormats.add(new SimpleDateFormat("yyyy/MM/dd"));
        alDateFormatsDesc.add("yyyyMMdd");
        alDateFormats.add(new SimpleDateFormat("yyyyMMdd"));
    };
    /**
     * constructor
     * @param sName Property name
     * @param bCustom Is custom proprety
     * @param bConstructor Is constructor property
     * @param bShouldBeDisplayed Does this standard property must be displayed (exp for ie is not)
     * @param bEditable Is this property editable 
     * @param bUnique Is this property value unique
     * @param cType Property type
     * @param format Property format.
     * @param oDefaultValue Default value
     */
    public PropertyMetaInformation(String sName,boolean bCustom,boolean bConstructor,
            boolean bShouldBeDisplayed,boolean bEditable,boolean bUnique,Class cType,
            Format format,Object oDefaultValue){
        this.sName = sName;
        this.bCustom = bCustom;
        this.bConstructor = bConstructor;
        this.bShouldBeDisplayed = bShouldBeDisplayed;
        this.bEditable = bEditable;
        this.bUnique = bUnique;
        this.format = format;
        this.cType = cType;
        this.oDefaultValue = oDefaultValue;
        if (cType.equals(Boolean.class)){
            if (oDefaultValue == null){
                this.oDefaultValue = Boolean.FALSE; //if no default is given, false for booleans
            }
            this.sHumanType = Messages.getString("Property_Format_Boolean");
        }
        else if (cType.equals(String.class)){
            if (oDefaultValue == null){
                this.oDefaultValue = ""; //if no default is given, ""
            }
            this.sHumanType = Messages.getString("Property_Format_String");
        }
        else if (cType.equals(Long.class)){
            if (oDefaultValue == null){
                this.oDefaultValue = 0l; //if no default is given, 0
            }
            this.sHumanType = Messages.getString("Property_Format_Number");
        }
        else if (cType.equals(Double.class)){
            if (oDefaultValue == null){
                this.oDefaultValue = 0.0d;  //if no default is given, 0.0
            }
            this.sHumanType = Messages.getString("Property_Format_Float");
        }
        else if (cType.equals(Date.class) ){
            //date default
            if (oDefaultValue == null){
                this.oDefaultValue = new Date();
            }
            else{
               this.oDefaultValue = oDefaultValue;
            }
            this.sHumanType = Messages.getString("Property_Format_Date");
        }
        else if (cType.equals(Class.class) ){
            this.oDefaultValue = Object.class;
        }
        else{ //class not supported
            Log.debug("Class not supported !!!");
        }
    }
        
    /**
     * @return
     */
    public Format getFormat() {
        return format;
    }

    /**
     * @param format
     */
    public void setFormat(Format format){
        this.format = format;
    }

    /**
     * @return
     */
    public boolean isConstructor() {
        return bConstructor;
    }

    /**
     * @return
     */
    public boolean isCustom() {
        return bCustom;
    }

    /**
     * @return
     */
    public Class getType() {
        return cType;
    }
    
    /**
     * @return
     */
    public String getName() {
        return sName;
    }
    
    /**
     * <property name='toto' custom ='true' constructor='true'  type='date' format='YYYYMMDD'/>
     * @return property meta information XML description
     */
    public String toXML(){
        String sDefault = "";
        try {
            if (oDefaultValue != null && format != null){
                sDefault = Util.format(oDefaultValue,this);
            }
        } catch (Exception e) { //should to occur at this point
            Log.error(e);
        }
        return "\t\t<"+XML_PROPERTY+" "+XML_NAME+"='"+sName+"' "+
            XML_CUSTOM+"='"+ bCustom+"' "+
            XML_CONSTRUCTOR+"='"+bConstructor+"' "+
            XML_VISIBLE+"='"+bShouldBeDisplayed+"' "+
            XML_EDITABLE+"='"+bEditable+"' "+
            XML_UNIQUE+"='"+bUnique+"' "+
            XML_TYPE+"='"+cType.getName()+"' "+
            XML_FORMAT+"='"+ (format==null ? "":getFormatDesc(format))+"' "+
            XML_DEFAULT_VALUE+"='"+sDefault+"'/>";
    }

    public Object getDefaultValue() {
        return oDefaultValue;
    }

    /*public void setDefaultValue(String defaultValue) {
        oDefaultValue = defaultValue;
    }*/
    
    public String toString(){
        return "Name="+sName+" Custom="+bCustom+" Constructor="+bConstructor
        +" Type="+cType+" Default="+oDefaultValue+" Format="
        + format+" Editable="+isEditable()+" Visible="+isVisible() 
        +" Unique="+isUnique();
    }

    public boolean isVisible() {
        return bShouldBeDisplayed;
    }

    public boolean isEditable() {
        return bEditable;
    }
    
     public boolean isUnique() {
        return bUnique;
    }

     /**
      * 
      * @return a human representation for a property type
      */
    public String getHumanType() {
        return sHumanType;
    }
    
    public static ArrayList<Format> getSupportedDateFormats(){
        return alDateFormats;
    }
    
    public static ArrayList<String> getSupportedDateFormatsDesc(){
        return alDateFormatsDesc;
    }
    
    public static Format getDateFormat(String sDesc){
        return alDateFormats.get(alDateFormatsDesc.indexOf(sDesc));
    }
    
    public static String getFormatDesc(Format format){
        return alDateFormatsDesc.get(alDateFormats.indexOf(format));
    }
    
}
