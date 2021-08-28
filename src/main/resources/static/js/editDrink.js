// POST SENDING TO REST API


$(document).ready(function () {

    $('#submit_but').click(function () {


        if (confirm('Do you really want to submit this recipe?')) {

            let url = new URL(window.location);
            let params = new URLSearchParams(url.search.slice(1));
            let drinkId = parseInt(params.get('id'));

            if (isNaN(drinkId) || drinkId === 0) {
                drinkId = "";
            }

            var obj = $('#form_data').serializeJSON();

            var jsonString = JSON.stringify(obj);
            // console.log(jsonString);

            // alert(jsonString);

            $.ajax({
                url: "/api/drink-management/" + drinkId,
                type: 'post',
                data: jsonString,
                success: function () {
                    let newURL = "";
                    if (drinkId == "") {

                        newURL = url.origin + '/list?page=1';

                    } else {

                        newURL = url.origin + '/single-view?drink=' + drinkId;
                    }
                    location.replace(newURL);

                },
                dataType: "json",
                contentType: "application/json"
            });
        }

    });


});


$(document).ready(function () {

    $("form[name='uploader']").submit(function(e) {

            var formData = new FormData($(this)[0]);

            $.ajax({
                url: "/add-image",
                type: "POST",
                data: formData,
                success: function (msg) {
                    console.log(msg)
                    alert(msg.toString())
                },
                cache: false,
                contentType: false,
                processData: false
            });

            e.preventDefault();



    });
});






$(document).ready(function () {
    var maxField = 10; //Input fields increment limitation
    var addButton = $('.add_button'); //Add button selector
    var wrapper = $('.add-group'); //Input field wrapper
    var initialIngredientsCount = document.getElementById("initialIngredientsCount").textContent.trim().toString();

    var x = initialIngredientsCount; //Initial field counter is 1

    var fieldHTML =
        '<div class=" sidebar-list" >' +
        '<div class="row justify-content-between " id="ingrMeasure" style="margin: 0px">' +

        '<div class=" md-3 col-5 ">' +
        '<input type="text" required maxlength="20" minlength="2" name="drinkIngredients[' + x.toString().trim() +  '][ingredient[name]]" value="" placeholder="Ingredient..." class="form-control"/>'+
        '<div class="invalid-feedback alert alert-danger">Input ingredient</div>\n' +
        '</div>' +

        '<div class=" md-3 col-5 ">' +
        '<input type="text" required maxlength="20" minlength="2" name="drinkIngredients[' + x.toString().trim() +'][measure[quantity]]" value="" placeholder="Quantity..." class="form-control"/>'+
        '<div class="invalid-feedback alert alert-danger">Input measure</div>\n' +
        '</div>' +


        '<a id="remove_blue" href="javascript:void(0);"' +
        ' class="remove_button col-1">' +
        '<svg class="bi bi-trash" width="1em" height="1em" viewBox="0 0 16 16" fill="currentColor" xmlns="http://www.w3.org/2000/svg">\n' +
        '<path d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0V6z"/>\n' +
        '<path fill-rule="evenodd" d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1v1zM4.118 4L4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4H4.118zM2.5 3V2h11v1h-11z"/>\n' +
        '</svg><br></a></div>'; //New input field html



    //Once add button is clicked
    $(addButton).click(function () {
        //Check maximum number of input fields
        if (x < maxField) {
            x++; //Increment field counter
            $(wrapper).append(fieldHTML); //Add field html
        }
    });

    //Once remove button is clicked
    $(wrapper).on('click', '.remove_button', function (e) {
        e.preventDefault();
        $(this).parent('div').remove(); //Remove field html
        x--; //Decrement field counter
    });
});


/*!
  SerializeJSON jQuery plugin.
  https://github.com/marioizquierdo/jquery.serializeJSON
  version 2.9.0 (Jan, 2018)

  Copyright (c) 2012-2018 Mario Izquierdo
  Dual licensed under the MIT (http://www.opensource.org/licenses/mit-license.php)
  and GPL (http://www.opensource.org/licenses/gpl-license.php) licenses.
*/
(function (factory) {
    if (typeof define === 'function' && define.amd) { // AMD. Register as an anonymous module.
        define(['jquery'], factory);
    } else if (typeof exports === 'object') { // Node/CommonJS
        var jQuery = require('jquery');
        module.exports = factory(jQuery);
    } else { // Browser globals (zepto supported)
        factory(window.jQuery || window.Zepto || window.$); // Zepto supported on browsers as well
    }

}(function ($) {
    "use strict";

    // jQuery('form').serializeJSON()
    $.fn.serializeJSON = function (options) {
        var f, $form, opts, formAsArray, serializedObject, name, value, parsedValue, _obj, nameWithNoType, type, keys,
            skipFalsy;
        f = $.serializeJSON;
        $form = this; // NOTE: the set of matched elements is most likely a form, but it could also be a group of inputs
        opts = f.setupOpts(options); // calculate values for options {parseNumbers, parseBoolens, parseNulls, ...} with defaults

        // Use native `serializeArray` function to get an array of {name, value} objects.
        formAsArray = $form.serializeArray();
        f.readCheckboxUncheckedValues(formAsArray, opts, $form); // add objects to the array from unchecked checkboxes if needed

        // Convert the formAsArray into a serializedObject with nested keys
        serializedObject = {};
        $.each(formAsArray, function (i, obj) {
            name = obj.name; // original input name
            value = obj.value; // input value
            _obj = f.extractTypeAndNameWithNoType(name);
            nameWithNoType = _obj.nameWithNoType; // input name with no type (i.e. "foo:string" => "foo")
            type = _obj.type; // type defined from the input name in :type colon notation
            if (!type) type = f.attrFromInputWithName($form, name, 'data-value-type');
            f.validateType(name, type, opts); // make sure that the type is one of the valid types if defined

            if (type !== 'skip') { // ignore inputs with type 'skip'
                keys = f.splitInputNameIntoKeysArray(nameWithNoType);
                parsedValue = f.parseValue(value, name, type, opts); // convert to string, number, boolean, null or customType

                skipFalsy = !parsedValue && f.shouldSkipFalsy($form, name, nameWithNoType, type, opts); // ignore falsy inputs if specified
                if (!skipFalsy) {
                    f.deepSet(serializedObject, keys, parsedValue, opts);
                }
            }
        });
        return serializedObject;
    };

    // Use $.serializeJSON as namespace for the auxiliar functions
    // and to define defaults
    $.serializeJSON = {

        defaultOptions: {
            checkboxUncheckedValue: undefined, // to include that value for unchecked checkboxes (instead of ignoring them)

            parseNumbers: false, // convert values like "1", "-2.33" to 1, -2.33
            parseBooleans: false, // convert "true", "false" to true, false
            parseNulls: false, // convert "null" to null
            parseAll: false, // all of the above
            parseWithFunction: null, // to use custom parser, a function like: function(val){ return parsed_val; }

            skipFalsyValuesForTypes: [], // skip serialization of falsy values for listed value types
            skipFalsyValuesForFields: [], // skip serialization of falsy values for listed field names

            customTypes: {}, // override defaultTypes
            defaultTypes: {
                "string": function (str) {
                    return String(str);
                },
                "number": function (str) {
                    return Number(str);
                },
                "boolean": function (str) {
                    var falses = ["false", "null", "undefined", "", "0"];
                    return falses.indexOf(str) === -1;
                },
                "null": function (str) {
                    var falses = ["false", "null", "undefined", "", "0"];
                    return falses.indexOf(str) === -1 ? str : null;
                },
                "array": function (str) {
                    return JSON.parse(str);
                },
                "object": function (str) {
                    return JSON.parse(str);
                },
                "auto": function (str) {
                    return $.serializeJSON.parseValue(str, null, null, {
                        parseNumbers: true,
                        parseBooleans: true,
                        parseNulls: true
                    });
                }, // try again with something like "parseAll"
                "skip": null // skip is a special type that makes it easy to ignore elements
            },

            useIntKeysAsArrayIndex: true // name="foo[2]" value="v" => {foo: [null, null, "v"]}, instead of {foo: ["2": "v"]}
        },

        // Merge option defaults into the options
        setupOpts: function (options) {
            var opt, validOpts, defaultOptions, optWithDefault, parseAll, f;
            f = $.serializeJSON;

            if (options == null) {
                options = {};
            }   // options ||= {}
            defaultOptions = f.defaultOptions || {}; // defaultOptions

            // Make sure that the user didn't misspell an option
            validOpts = ['checkboxUncheckedValue', 'parseNumbers', 'parseBooleans', 'parseNulls', 'parseAll', 'parseWithFunction', 'skipFalsyValuesForTypes', 'skipFalsyValuesForFields', 'customTypes', 'defaultTypes', 'useIntKeysAsArrayIndex']; // re-define because the user may override the defaultOptions
            for (opt in options) {
                if (validOpts.indexOf(opt) === -1) {
                    throw new Error("serializeJSON ERROR: invalid option '" + opt + "'. Please use one of " + validOpts.join(', '));
                }
            }

            // Helper to get the default value for this option if none is specified by the user
            optWithDefault = function (key) {
                return (options[key] !== false) && (options[key] !== '') && (options[key] || defaultOptions[key]);
            };

            // Return computed options (opts to be used in the rest of the script)
            parseAll = optWithDefault('parseAll');
            return {
                checkboxUncheckedValue: optWithDefault('checkboxUncheckedValue'),

                parseNumbers: parseAll || optWithDefault('parseNumbers'),
                parseBooleans: parseAll || optWithDefault('parseBooleans'),
                parseNulls: parseAll || optWithDefault('parseNulls'),
                parseWithFunction: optWithDefault('parseWithFunction'),

                skipFalsyValuesForTypes: optWithDefault('skipFalsyValuesForTypes'),
                skipFalsyValuesForFields: optWithDefault('skipFalsyValuesForFields'),
                typeFunctions: $.extend({}, optWithDefault('defaultTypes'), optWithDefault('customTypes')),

                useIntKeysAsArrayIndex: optWithDefault('useIntKeysAsArrayIndex')
            };
        },

        // Given a string, apply the type or the relevant "parse" options, to return the parsed value
        parseValue: function (valStr, inputName, type, opts) {
            var f, parsedVal;
            f = $.serializeJSON;
            parsedVal = valStr; // if no parsing is needed, the returned value will be the same

            if (opts.typeFunctions && type && opts.typeFunctions[type]) { // use a type if available
                parsedVal = opts.typeFunctions[type](valStr);
            } else if (opts.parseNumbers && f.isNumeric(valStr)) { // auto: number
                parsedVal = Number(valStr);
            } else if (opts.parseBooleans && (valStr === "true" || valStr === "false")) { // auto: boolean
                parsedVal = (valStr === "true");
            } else if (opts.parseNulls && valStr == "null") { // auto: null
                parsedVal = null;
            } else if (opts.typeFunctions && opts.typeFunctions["string"]) { // make sure to apply :string type if it was re-defined
                parsedVal = opts.typeFunctions["string"](valStr);
            }

            // Custom parse function: apply after parsing options, unless there's an explicit type.
            if (opts.parseWithFunction && !type) {
                parsedVal = opts.parseWithFunction(parsedVal, inputName);
            }

            return parsedVal;
        },

        isObject: function (obj) {
            return obj === Object(obj);
        }, // is it an Object?
        isUndefined: function (obj) {
            return obj === void 0;
        }, // safe check for undefined values
        isValidArrayIndex: function (val) {
            return /^[0-9]+$/.test(String(val));
        }, // 1,2,3,4 ... are valid array indexes
        isNumeric: function (obj) {
            return obj - parseFloat(obj) >= 0;
        }, // taken from jQuery.isNumeric implementation. Not using jQuery.isNumeric to support old jQuery and Zepto versions

        optionKeys: function (obj) {
            if (Object.keys) {
                return Object.keys(obj);
            } else {
                var key, keys = [];
                for (key in obj) {
                    keys.push(key);
                }
                return keys;
            }
        }, // polyfill Object.keys to get option keys in IE<9


        // Fill the formAsArray object with values for the unchecked checkbox inputs,
        // using the same format as the jquery.serializeArray function.
        // The value of the unchecked values is determined from the opts.checkboxUncheckedValue
        // and/or the data-unchecked-value attribute of the inputs.
        readCheckboxUncheckedValues: function (formAsArray, opts, $form) {
            var selector, $uncheckedCheckboxes, $el, uncheckedValue, f, name;
            if (opts == null) {
                opts = {};
            }
            f = $.serializeJSON;

            selector = 'input[type=checkbox][name]:not(:checked):not([disabled])';
            $uncheckedCheckboxes = $form.find(selector).add($form.filter(selector));
            $uncheckedCheckboxes.each(function (i, el) {
                // Check data attr first, then the option
                $el = $(el);
                uncheckedValue = $el.attr('data-unchecked-value');
                if (uncheckedValue == null) {
                    uncheckedValue = opts.checkboxUncheckedValue;
                }

                // If there's an uncheckedValue, push it into the serialized formAsArray
                if (uncheckedValue != null) {
                    if (el.name && el.name.indexOf("[][") !== -1) { // identify a non-supported
                        throw new Error("serializeJSON ERROR: checkbox unchecked values are not supported on nested arrays of objects like '" + el.name + "'. See https://github.com/marioizquierdo/jquery.serializeJSON/issues/67");
                    }
                    formAsArray.push({name: el.name, value: uncheckedValue});
                }
            });
        },

        // Returns and object with properties {name_without_type, type} from a given name.
        // The type is null if none specified. Example:
        //   "foo"           =>  {nameWithNoType: "foo",      type:  null}
        //   "foo:boolean"   =>  {nameWithNoType: "foo",      type: "boolean"}
        //   "foo[bar]:null" =>  {nameWithNoType: "foo[bar]", type: "null"}
        extractTypeAndNameWithNoType: function (name) {
            var match;
            if (match = name.match(/(.*):([^:]+)$/)) {
                return {nameWithNoType: match[1], type: match[2]};
            } else {
                return {nameWithNoType: name, type: null};
            }
        },


        // Check if this input should be skipped when it has a falsy value,
        // depending on the options to skip values by name or type, and the data-skip-falsy attribute.
        shouldSkipFalsy: function ($form, name, nameWithNoType, type, opts) {
            var f = $.serializeJSON;

            var skipFromDataAttr = f.attrFromInputWithName($form, name, 'data-skip-falsy');
            if (skipFromDataAttr != null) {
                return skipFromDataAttr !== 'false'; // any value is true, except if explicitly using 'false'
            }

            var optForFields = opts.skipFalsyValuesForFields;
            if (optForFields && (optForFields.indexOf(nameWithNoType) !== -1 || optForFields.indexOf(name) !== -1)) {
                return true;
            }

            var optForTypes = opts.skipFalsyValuesForTypes;
            if (type == null) type = 'string'; // assume fields with no type are targeted as string
            if (optForTypes && optForTypes.indexOf(type) !== -1) {
                return true
            }

            return false;
        },

        // Finds the first input in $form with this name, and get the given attr from it.
        // Returns undefined if no input or no attribute was found.
        attrFromInputWithName: function ($form, name, attrName) {
            var escapedName, selector, $input, attrValue;
            escapedName = name.replace(/(:|\.|\[|\]|\s)/g, '\\$1'); // every non-standard character need to be escaped by \\
            selector = '[name="' + escapedName + '"]';
            $input = $form.find(selector).add($form.filter(selector)); // NOTE: this returns only the first $input element if multiple are matched with the same name (i.e. an "array[]"). So, arrays with different element types specified through the data-value-type attr is not supported.
            return $input.attr(attrName);
        },

        // Raise an error if the type is not recognized.
        validateType: function (name, type, opts) {
            var validTypes, f;
            f = $.serializeJSON;
            validTypes = f.optionKeys(opts ? opts.typeFunctions : f.defaultOptions.defaultTypes);
            if (!type || validTypes.indexOf(type) !== -1) {
                return true;
            } else {
                throw new Error("serializeJSON ERROR: Invalid type " + type + " found in input name '" + name + "', please use one of " + validTypes.join(', '));
            }
        },


        // Split the input name in programatically readable keys.
        // Examples:
        // "foo"              => ['foo']
        // "[foo]"            => ['foo']
        // "foo[inn][bar]"    => ['foo', 'inn', 'bar']
        // "foo[inn[bar]]"    => ['foo', 'inn', 'bar']
        // "foo[inn][arr][0]" => ['foo', 'inn', 'arr', '0']
        // "arr[][val]"       => ['arr', '', 'val']
        splitInputNameIntoKeysArray: function (nameWithNoType) {
            var keys, f;
            f = $.serializeJSON;
            keys = nameWithNoType.split('['); // split string into array
            keys = $.map(keys, function (key) {
                return key.replace(/\]/g, '');
            }); // remove closing brackets
            if (keys[0] === '') {
                keys.shift();
            } // ensure no opening bracket ("[foo][inn]" should be same as "foo[inn]")
            return keys;
        },

        // Set a value in an object or array, using multiple keys to set in a nested object or array:
        //
        // deepSet(obj, ['foo'], v)               // obj['foo'] = v
        // deepSet(obj, ['foo', 'inn'], v)        // obj['foo']['inn'] = v // Create the inner obj['foo'] object, if needed
        // deepSet(obj, ['foo', 'inn', '123'], v) // obj['foo']['arr']['123'] = v //
        //
        // deepSet(obj, ['0'], v)                                   // obj['0'] = v
        // deepSet(arr, ['0'], v, {useIntKeysAsArrayIndex: true})   // arr[0] = v
        // deepSet(arr, [''], v)                                    // arr.push(v)
        // deepSet(obj, ['arr', ''], v)                             // obj['arr'].push(v)
        //
        // arr = [];
        // deepSet(arr, ['', v]          // arr => [v]
        // deepSet(arr, ['', 'foo'], v)  // arr => [v, {foo: v}]
        // deepSet(arr, ['', 'bar'], v)  // arr => [v, {foo: v, bar: v}]
        // deepSet(arr, ['', 'bar'], v)  // arr => [v, {foo: v, bar: v}, {bar: v}]
        //
        deepSet: function (o, keys, value, opts) {
            var key, nextKey, tail, lastIdx, lastVal, f;
            if (opts == null) {
                opts = {};
            }
            f = $.serializeJSON;
            if (f.isUndefined(o)) {
                throw new Error("ArgumentError: param 'o' expected to be an object or array, found undefined");
            }
            if (!keys || keys.length === 0) {
                throw new Error("ArgumentError: param 'keys' expected to be an array with least one element");
            }

            key = keys[0];

            // Only one key, then it's not a deepSet, just assign the value.
            if (keys.length === 1) {
                if (key === '') {
                    o.push(value); // '' is used to push values into the array (assume o is an array)
                } else {
                    o[key] = value; // other keys can be used as object keys or array indexes
                }

                // With more keys is a deepSet. Apply recursively.
            } else {
                nextKey = keys[1];

                // '' is used to push values into the array,
                // with nextKey, set the value into the same object, in object[nextKey].
                // Covers the case of ['', 'foo'] and ['', 'var'] to push the object {foo, var}, and the case of nested arrays.
                if (key === '') {
                    lastIdx = o.length - 1; // asume o is array
                    lastVal = o[lastIdx];
                    if (f.isObject(lastVal) && (f.isUndefined(lastVal[nextKey]) || keys.length > 2)) { // if nextKey is not present in the last object element, or there are more keys to deep set
                        key = lastIdx; // then set the new value in the same object element
                    } else {
                        key = lastIdx + 1; // otherwise, point to set the next index in the array
                    }
                }

                // '' is used to push values into the array "array[]"
                if (nextKey === '') {
                    if (f.isUndefined(o[key]) || !$.isArray(o[key])) {
                        o[key] = []; // define (or override) as array to push values
                    }
                } else {
                    if (opts.useIntKeysAsArrayIndex && f.isValidArrayIndex(nextKey)) { // if 1, 2, 3 ... then use an array, where nextKey is the index
                        if (f.isUndefined(o[key]) || !$.isArray(o[key])) {
                            o[key] = []; // define (or override) as array, to insert values using int keys as array indexes
                        }
                    } else { // for anything else, use an object, where nextKey is going to be the attribute name
                        if (f.isUndefined(o[key]) || !f.isObject(o[key])) {
                            o[key] = {}; // define (or override) as object, to set nested properties
                        }
                    }
                }

                // Recursively set the inner object
                tail = keys.slice(1);
                f.deepSet(o[key], tail, value, opts);
            }
        }

    };

}));





