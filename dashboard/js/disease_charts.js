/*
 *  Copyright (c) 2015, Parse, LLC. All rights reserved.
 *
 *  You are hereby granted a non-exclusive, worldwide, royalty-free license to
 *  use, copy, modify, and distribute this software in source code or binary
 *  form for use in connection with the web services and APIs provided by Parse.
 *
 *  As with any software that integrates with the Parse platform, your use of
 *  this software is subject to the Parse Terms of Service
 *  [https://www.parse.com/about/terms]. This copyright notice shall be
 *  included in all copies or substantial portions of the software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 *  FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
 *  IN THE SOFTWARE.
 *
 */

var Parse = require('parse').Parse;
// ParseReact sits on top of your Parse singleton
var ParseReact = require('parse-react');
var React = require('react');
var ParseComponent = ParseReact.Component(React);

var diseaseStat = require('./disease_stats.js');

// Top-Level component that binds to Parse using the ParseReact Mixin.
// This should help demonstrate the "It's Just That Easy" potential here.
export default class DiseaseCharts extends ParseComponent {
  observe(props, state) {
    return {
      items: new Parse.Query('disease_base').ascending('updatedAt')
    };
  }

  render() {
    var items = this.data.items;
    var numVector = 0;
    var numFood = 0;
    var numWater = 0;
    var numAir = 0;
    var i = 0;
    for (i=0; i < items.length; i++) {
      if(items[i].diseaseType == 'Food') {
        numFood++;
      }
      else if(items[i].diseaseType == 'Air') {
        numAir++;
      }
      else if(items[i].diseaseType == 'Vector') {
        numVector++;
      }
      else if(items[i].diseaseType == 'Water') {
        numWater++;
      }
    }

    var centVector = numVector / items.length * 100;
    var centFood = numFood / items.length * 100;
    var centWater = numWater / items.length * 100;
    var centAir = numAir / items.length * 100;

    var data = [
      ['Vector', 'Food', 'Water', 'Air'],
      [centVector, centFood, centWater, centAir]
      ];
    var options = {'title':'Disease History',
                    'width':400,
                    'height':300};
    return (
      // <li>
      //   numVector: {centVector}
      //   <br/>
      //   numWater: {centWater}
      //   <br/>
      //   numAir: {centAir}
      //   <br/>
      //   numFood: {centFood}
      // </li>
      <diseaseStat data={data} options={options}></diseaseStat>
    );
  }
}
