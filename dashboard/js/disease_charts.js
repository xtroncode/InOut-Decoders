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
var React = require('react');
// ParseReact sits on top of your Parse singleton
var ParseReact = require('parse-react');
var ParseComponent = ParseReact.Component(React);
var Chart = require('react-google-charts').Chart;

// var diseaseStat = require('./disease_stats.js');

// Top-Level component that binds to Parse using the ParseReact Mixin.
// This should help demonstrate the "It's Just That Easy" potential here.
export default class DiseaseCharts extends ParseComponent {
  observe(props, state) {
    return {
      disease_class: new Parse.Query('disease_base').ascending('updatedAt'),
      medicine_class: new Parse.Query('MedCase').ascending('Medicines')
    };
  }

  render() {
    var disease_class = this.data.disease_class;
    var medicine_class = this.data.medicine_class;

    // Number crunching with Disease classification Data
    var numVector = 0;
    var numFood = 0;
    var numWater = 0;
    var numAir = 0;
    var i = 0;
    for (i=0; i < disease_class.length; i++) {
      if(disease_class[i].diseaseType == 'Food') {
        numFood++;
      }
      else if(disease_class[i].diseaseType == 'Air') {
        numAir++;
      }
      else if(disease_class[i].diseaseType == 'Vector') {
        numVector++;
      }
      else if(disease_class[i].diseaseType == 'Water') {
        numWater++;
      }
    }

    var centVector = numVector / disease_class.length * 100;
    var centFood = numFood / disease_class.length * 100;
    var centWater = numWater / disease_class.length * 100;
    var centAir = numAir / disease_class.length * 100;

    var disease_data = [
      ['Disease', 'Percentage'],
      // ['Vector', 'Food', 'Water', 'Air'],
      ['Vector', centVector],
      ['Food', centFood],
      ['Water', centWater],
      ['Air', centAir]
      // [1, 2, 3, 4]
      ];
    var disease_options = {'title':'Disease Statistics',
                    'width':580,
                    'height':360};

    // Number crunching with Medicine classification code
    // var med=['Crocin','Paracetamol','Crocin','DCold'];
    var med = [];
    for (i=0; i < medicine_class.length; i++) {
      med[i] = medicine_class[i].Medicines;
    }
    // var med = medicine_class.Medicines;

  	var med_maps={};
  	for(i=0;i<med.length;i++) {
  		med_maps[med[i]]=0;
  	}

  	for(i=0;i<med.length;i++){
      med_maps[med[i]]++;
    }

    var med_data=[];
    med_data[0] = ['Medicine', 'Dose Count'];
    var med_keys=Object.keys(med);
    var j=0;
    for(i in med_maps)
    {
      med_data[++j]=[i,parseInt(med_maps[i])];
      //alert(i+med_maps[i]);
      // med_data[i]=['Vector',centVector];
    }
    // var med_data = [['Medicine', 'Count']];
    // for (var i = 0; i < med_maps.length; i++) {
    //   med_data[i] = [med_keys[i], med_maps[med[i]]];
    // }

    var med_options = {'title':'Medicine Statistics',
                    'width':580,
                    'height':360};

    // Returning the graph objects for rendering
    return (
      <div>
        <div className="BarChart">
          // <h3> Bar Chart </h3>
          <Chart chartType='PieChart' data={disease_data} options={disease_options} graph_id="barchart_graph"/>
        </div>
        <div className="MedicineChart">
          // <h3> Medicine Chart </h3>
          <Chart chartType='BarChart' data={med_data} options={med_options} graph_id="barchart_graph2"/>
        </div>
      </div>
      // <li>
      //   {centVector}
      // </li>
    );
  }
}
