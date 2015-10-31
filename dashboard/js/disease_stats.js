var React = require('react');
var Chart = require('react-google-charts').Chart;

var diseaseStat = React.createClass({
	// getInitialState: function() {
	// 	return {
	// 		BarChart: {
	// 			data: [],
	// 			chartType: "",
	// 			options : {}
	// 		}
	// 	};
	// },
	// componentDidMount: function() {
	// 	var BarChart = {
	// 		data : this.props.data,
	// 		options: this.props.options,
	// 		chartType: "BarChart",
	// 		div_id: "BarChart"
	// 	};
	//
	// 	this.setState({
	// 		'BarChart': BarChart
	// 	});
	//
	// },

	render: function() {
		var ai = 2;
		return (
			<div>
				{ai}
			</div>
			// <div className="BarChart">
			// 	<h3> Bar Chart </h3>
			// 	<Chart chartType='BarChart' data={this.props.data} options={this.state.BarChart.options} graph_id="barchart_graph"/>
			// </div>
		);
	}
});

module.exports = diseaseStat;
