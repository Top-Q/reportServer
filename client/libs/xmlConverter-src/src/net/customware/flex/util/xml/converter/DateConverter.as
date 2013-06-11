package net.customware.flex.util.xml.converter{
    import mx.formatters.DateFormatter;
    import mx.controls.DateField;
    import net.customware.flex.util.xml.XMLConverter;
    /*
    * Simple date converter that uses a standard aussie friendly format.
    * It's possible to create your own instance of this class with your own format string.
    * Just create, and register, as it will override the standard one
    */
    public class DateConverter implements Converter{
        
        private var formatter:DateFormatter = new DateFormatter();
        
        function DateConverter(format:String = null) {
            if (format != null)
                formatter.formatString = format;
            else        
                formatter.formatString = "DD/MM/YYYY";
        }
        
        public function toXMLValue(result:XML, name:String, value:Object):void {
            result[ name ] =  formatter.format( value );    
        }
        
        public function fromXMLValue(result:Object, type:XML, xml:XML): void{
            XMLConverter.setValue(result, type, DateField.stringToDate( xml[type.@name], formatter.formatString ));
        }
        
    }
}