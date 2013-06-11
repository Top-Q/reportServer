package net.customware.flex.util.xml.converter
{
    public interface Converter {
        
        function toXMLValue(result:XML, name:String, obj:Object):void;

        function fromXMLValue(result:Object, type:XML, xml:XML):void;
        
    }
}