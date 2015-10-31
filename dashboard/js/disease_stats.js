var React = require('react');
var Chart = require('react-google-charts').Chart;

var diseaseStat = React.createClass({
	getInitialState: function() {
		return {
			BarChart: {
				data: [],
				chartType: "",
				options : {}
			}
		};
	},
	componentDidMount: function() {
		var BarChart = {
			data : this.props.data,
			options: this.props.options,
			chartType: "BarChart",
			div_id: "BarChart"
		};

		this.setState({
			'BarChart': BarChart
		});

	},

	render: function() {

		return (
			<div className="Examples">
				<h3> Bar Chart </h3>
				<Chart chartType={this.state.BarChart.chartType} width={"500px"} height={"300px"} data={this.state.BarChart.data} options = {this.state.BarChart.options}/>
			</div>
		);
	}
});
