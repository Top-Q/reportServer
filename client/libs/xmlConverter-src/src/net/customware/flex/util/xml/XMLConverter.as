package net.customware.flex.util.xml{
    import flash.utils.describeType;
    import flash.utils.getDefinitionByName;
    import flash.utils.getQualifiedClassName;
    
    import mx.collections.IList;
    import mx.core.Repeater;
    
    import net.customware.flex.util.xml.converter.*;
    import mx.collections.ArrayCollection;
    
    public class XMLConverter{

        /**
        * Map that stores all the aliases
        */ 
        private static var aliases:Object = new Object();
        
        /**
        * Map that stores all the converters
        */ 
        private static var converters:Object = new Object();
        
        /**
        * singleton instance of XMLConverter
        */ 
        private static var converter:XMLConverter;
        
        /**
        * Random number to try and stop code from not using getInstance();
        * http://www.berniecode.com/blog/2007/11/28/proper-private-constructors-for-actionscript-30/
        */ 
        private static var _num:Number = Math.random();
        
        /**
        * Constructor
        */ 
        public function XMLConverter( num:Number ){
            if ( _num != num )
                throw new Error( 'XMLConverter is a singleton, use getInstance();' );
        }
        
        /**
        * returns singleton instance of XMLConverter
        */ 
        public static function getInstance(): XMLConverter {
            if (converter == null ){
                converter = new XMLConverter(_num);
                converter.initialise();
            }
            return converter;
        }
        
        /**
        * For now all the constructor does is register a simple date converter. 
        * You can register your own, which will override the standard one, using 
        * a custom format
        */
        private function initialise(): void {
            registerConverter( Date, new DateConverter( "DD/MM/YYYY" ) );
            registerConverter( Array, new ArrayConverter() );
            registerConverter( ArrayCollection, new ArrayCollectionConverter() );
        }

        /*
        * This is where you register an alias. An alias is used to put more user friendly
        * element names within the xml, instead of using flex fully qualified class names
        */
        public function registerAlias( name:String, clazz:Class ):void {
            aliases[ getQualifiedClassName( clazz ) ] = name;            
        }
        
        /*
        * Returns the fully qualified class name for
        * the alias that is passed in. If no class is found, it returns null
        */
        private function getClassForAlias( alias:String ):String {
            for ( var k:String in aliases ) {
                if ( aliases[k] == alias )
                    return k;
            }            
            return null;
        }
        
        /*
        * Register a Converter. When the class that your converter is registered with is found,
        * it's called to do the xml conversion
        */
        public function registerConverter( clazz:Class, converter:Converter ):void {
            converters[ getQualifiedClassName( clazz ) ] = converter;
        }

        /*
        * Formats fully qualified class names to XML friendly elements
        */
        private function formatNodeName( nodeName:String ):String{
            return nodeName.replace( "::", "_" );
        }
        
        /*
        * Formats previously formatted XML friendly elements to
        * fully qualified class names
        */
        private function unformatNodeName(nodeName:String):String{
            return nodeName.replace( "_", "::" );
        }
        
        /*
        * The variables XMLList needs to be the variables list from the describeType()
        * function. This finds the relevant XML entry for the given name.
        * If none can be found, it returns null
        */ 
        private function getVariable( type:XML, name:String ):XML {
            if ( name.charAt( 0 ) == '@' )
                name = name.substr( 1 );
            var result:XML = getVariableInList(type.variable, name);
            if (result == null)
                result = getVariableInList(type.accessor, name);
            if (result == null)
                result = findGetMethod(type.method, name);
            return result;
        }
        
        private function getVariableInList(list:XMLList, name:String ) :XML {
            for ( var i:int = 0; i < list.length(); i++ ){
                if ( list[ i ].@name == name )
                    return list[ i ];
            }
            return null;
        }
        
        private function findGetMethod(list:XMLList, name:String):XML{
            var methodName:String = "get" + name.substr(0,1).toUpperCase() + name.substr(1);
            for ( var i:int = 0; i < list.length(); i++ ){
                if ( list[ i ].@name == methodName ){
                    list[i].@type = list[i].@returnType;
                    list[i].@name = name;
                    return list[ i ];
                }
            }
            return null;
        }
        
        private function instantiateObject(className:String) : Object {
            try{
                var clazz:Class = getDefinitionByName( className ) as Class; 
            } catch ( e:Error ){
                // if there's an error chances are the alias isn't added and/or the class can't be found
                throw Error( "The XML contains an element called '" + className + "' which doesn't have a corresponding alias class, or cannot be found" );
            }
            // create an instance of the object we're about to build
            return new clazz();
        }
        
        public static function setValue(target:Object, variable:XML, value:Object): void {
            var tempName:String = variable.@name;
            if (tempName.charAt(0) == '@')
                tempName = tempName.substr(1);
            if (variable.name() == "accessor" || variable.name() == "variable" )
                target[tempName] = value;    
            else if (variable.name() == "method"){
                var methodName:String = "set" + tempName.substr(0,1).toUpperCase() + tempName.substr(1);
                target[methodName](value);
            }        
        }
        
        public static function getValue(target:Object, variable:XML, objAttrName:String): Object {
            if (variable.name() == "accessor" || variable.name() == "variable" )
                return target[objAttrName];
            else if (variable.name() == "method"){
                var methodName:String = "get" + objAttrName.substr(0,1).toUpperCase() + objAttrName.substr(1);
                return target[methodName]();
            }        
            return null;
        }
        
        
        /*
        * This function uses reflection and registered converters to convert
        * simple objects to XML.
        */
        public function toXML( obj:Object ):XML {
            // first we get the fully qualified class name
            var nodeName:String = getQualifiedClassName( obj );
            // now we see if we have an alias for it
            if ( aliases[ nodeName ] != null )
                // if there's an alias we set the nodeName accordingly
                nodeName = aliases[nodeName];
            else
                // otherwise we use an automatically generated one
                nodeName = formatNodeName( nodeName );
            // create the root xml object
            var result:XML = new XML( "<" + nodeName + "></" + nodeName + ">" );
            
            // get the type description, and then get the attributes (variables)
            var type:XML = describeType( obj );
            var vars:XMLList = type.variable;
            
            // if the object we're converting has a preference on how the xml is to be created, use it
            if ( obj is XMLElementOrderable ) {
                // get the array of variables that we're to convert in order
                var elements:Array = XMLElementOrderable( obj ).getElementOrder();
                for ( var i:int = 0; i < elements.length; i++ ){
                    // get the corresponding describeType variable XML entry for the element
                    var temp:XML = getVariable( type, elements[ i ] );
                    // if none exists, then the Array points to a variable that isn't there!
                    if ( temp == null )
                        throw Error( "Property '" + elements[ i ]+ "' doesn't exist in class '" + getQualifiedClassName( obj ) +"'" );
                    // if the variable starts with a '@' then we're about to convert into an attribute
                    // so we kinda hack the variable XML entry with the @ character
                    if ( elements[ i ].toString().charAt( 0 ) == '@' )
                        temp.@name = '@' + temp.@name;                
                    // convert the property to xml    
                    propToXML( result, temp, obj );
                }
            }
            else {  
                // The object doesn't implement our interface, so we'll just convert in the order  
                // that describeType() has given us
                for ( var j:int = 0; j < vars.length(); j++ ){
                    propToXML( result, vars[j], obj );
                }
            }
            return result;
        }
        
        /*
        * This function converts individual properties to XML
        */
        private function propToXML( output:XML, variable:XML, obj:Object ):void{
            // get the variable name 
            var objAttrName:String = variable.@name;
            // if it starts with '@' then we're doing an attribute. 
            if ( objAttrName.toString().charAt(0) == '@' )
                // keep a copy of the variable name without the @, so we know
                // which variable of the object we need
                objAttrName = objAttrName.toString().substr( 1 );
            // get the value from the object we're converting
            var value:Object = getValue(obj, variable, objAttrName);

            var nodeName:String = variable.@name;
            // if the variable value is null, then we set the xml entry to an empty string
            if ( value == null ){
                output[ variable.@name ] = "";
                return;
            }
            // check if we have a registered converter for the variable class type
            var converter:Converter = Converter( converters[ variable.@type ] );
            // if the variable is of a standard type, and the user hasn't specified
            // a custom converter, then we just copy the value over
            if ( ( variable.@type == "String" || variable.@type == "int" ||
                 variable.@type == "Boolean" || variable.@type == "Number") && converter == null) {
                output[ nodeName ] = value;
            } else if ( converter == null ){
                // else we have an object which isn't an array, which doesn't have a converter
                var child:XML = new XML( "<" + nodeName + "></" + nodeName + ">" );
                child = toXML( value );
                child.setName( variable.@name );
                output[ variable.@name ] = child;
            } else {
                // call the converter to do the work for us
                converter.toXMLValue(output, nodeName, value);
            }
        }

        /*
        * converts the given xml to an object
        */
        public function fromXML( xml:XML, nodeType:String = null ):Object {
            // work out the type of object we're about to convert into
            var className:String = nodeType;
            // see if there's an alias
            if ( className == null )
                className = getClassForAlias( unformatNodeName( xml.name() ) );            
           // if there's no alias, unformat the node name        
            if  ( className == null )
                className = unformatNodeName( xml.name() );
            // attempt to create the class 
            var result:Object = instantiateObject(className);
            // get the variables in the object
            var type:XML = describeType( result );
            
            var vars:XMLList = type.variable;
            // does the object have a preference on how it ought to be built?    
            if (result is XMLElementOrderable){
                // yes it does, so get the list of elements we're to convert
                var elements:Array = XMLElementOrderable( result ).getElementOrder();
                for ( var i:int = 0; i < elements.length; i++ ){
                    var temp:XML = getVariable( type, elements[ i ] );
                    // if the element starts with '@', then we're doing an attribute
                    if ( elements[ i ].toString().charAt( 0 ) == '@' )
                        temp.@name = '@' + temp.@name;                
                    propFromXML( result, temp, xml );
                }                
            }
            else {
                // no preference, so let describeType() dictate how it gets converted
                for ( var j:int = 0; j < vars.length(); j++ ) {
                    propFromXML( result, vars[ j ], xml );
                }
            }
            return result;
        }
        
        /*
        * converts an individual property from XML 
        */ 
        private function propFromXML( result:Object, variable:XML, xml:XML ):void {
            // get the name of the variable we're about to convert     
            var nodeName:String = variable.@name;
            // setup a copy of the name, for the object, in case we're doing an attribute   
            var objAttrName:String = variable.@name;
            // if we're doing an attribute, remove the '@'
            if ( objAttrName.toString().charAt(0) == '@' )
                objAttrName = objAttrName.toString().substr( 1 );
            // check to see whether there's a converter for the current property    
            var converter:Converter = Converter( converters[ variable.@type ] );


            // if it's a standard variable type and no converter is registered, just convert ourselves
            if ( (variable.@type == "String" || variable.@type ==  "Boolean" || 
                  variable.@type == "int" || variable.@type == "Number") && converter == null ) {
                setValue(result, variable, xml[ nodeName ]);
            }
            else if ( converter == null ){
                var childXML:XML = xml[ nodeName ][ 0 ];
                if ( childXML != null && childXML.children().length() > 0 ) {
                    var child:Object = fromXML( childXML, variable.@type);
                    setValue(result, variable, child );
                }
            } else {
               converter.fromXMLValue( result, variable, xml );
            } 
        }
    }
}