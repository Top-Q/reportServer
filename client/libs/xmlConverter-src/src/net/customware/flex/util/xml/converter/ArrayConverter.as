package net.customware.flex.util.xml.converter{
    import net.customware.flex.util.xml.XMLConverter;
    
    
    public class ArrayConverter implements Converter {

        public function fromXMLValue(result:Object, type:XML, xml:XML): void{
            var xmlList:XMLList =  xml[ type.@name ].children();                    
            var tempArray:Array = new Array();
            for ( var i:int = 0; i< xmlList.length(); i++ ) {
                tempArray [ i ] = XMLConverter.getInstance().fromXML( xmlList[ i ] );
            }
            if (tempArray.length > 0)
                XMLConverter.setValue(result, type, tempArray);
        }
        
        public function toXMLValue(result:XML, name:String, value:Object):void        {
            // if the variable is an array, then we need to iterate over it and 
            // convert the elements individually
            if ( value.length == 0 ){
                result[ name ] = "";
                return;
            }
            // create the parent xml node for the array
            var child:XML = new XML( "<" + name + "></" + name + ">" );
            // iterate over the elements of the array, for now there is a limitation,
            // elements of the array have to be custom value objects
            for ( var i:int = 0; i < value.length; i++ ) {
                var tmp:XML = XMLConverter.getInstance().toXML( value[ i ] );
                child[ tmp.name() ][ i ] = tmp;
            }
            result[ name ] = child;
        }
    }
}