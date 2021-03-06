package com.emiliano.fmframework.optimization.aggregateFunctions;

public interface AggregateFunction {
	public double operate(double operand1,double operand2);
	public double getNeutralElement();
	public double inverseOperator(double operand1,double operand2);
}
