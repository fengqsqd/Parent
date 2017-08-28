package cn.edu.hdu.lab.dao.tmc;

import java.util.ArrayList;
import java.util.List;

public class Tmc {
	private String tmcType; //����Ʒ��������ͣ�SeqMarkov��UCMarkov��SoftMarkov;
	private String names;   //Markov����Ӧ����������������;
	private String owned;
	private State start;
	private List<State> states=new ArrayList<State>();
	private List<State> ends=new ArrayList<State>();
	private List<Transition> transitions=new ArrayList<Transition>();
	private String notation;//���������Markov chain ������ǰ������
	private double pro; //Ǩ�Ƹ���
	private boolean isCombine=false;
	public Tmc(){}
	
	public Tmc(State start)
	{
		this.start=start;
		this.states.add(start);
	}
	public void printTmc()
	{
		System.out.println("��Ӧ������ǰ��������"+notation);
		System.out.println("ִ�и��ʣ�"+pro);
		System.out.println("��ʼ״̬��"+start.getName());
		System.out.println("״̬����");
		for (State state : states) 
		{
			state.print_state();
		}
		System.out.println("Ǩ�Ƽ���");
		for (Transition transition : transitions) 
		{
			transition.print_transitition();
			//System.out.println(transition);
		}
		System.out.println("��ֹ״̬����");
		for (State state : ends) 
		{
			state.print_state();
		}
		
	}
	public void statesAddState(State state)
	{
		this.states.add(state);
	}
	public void endsAddState(State endState)
	{
		this.ends.add(endState);
	}
	public void endsRemoveState(State endState)
	{
		this.ends.remove(endState);
	}
	public void statesAddStates(List<State> states)
	{
		this.states.addAll(states);
	}
	public void endsAddStates(List<State> states)
	{
		this.ends.addAll(states);
	}
	public void transitionsAddtransition(Transition trans)
	{
		this.transitions.add(trans);
	}
	public void transitionsAddtransitions(List<Transition> transitions)
	{
		this.transitions.addAll(transitions);
	}
	public State getStart() {
		return start;
	}
	public void setStart(State start) {
		this.start = start;
	}
	public List<State> getStates() {
		return states;
	}
	public void setStates(List<State> states) {
		this.states = states;
	}
	public List<State> getEnds() {
		return ends;
	}
	public void setEnds(List<State> ends) {
		this.ends = ends;
	}
	public List<Transition> getTransitions() {
		return transitions;
	}
	public void setTransitions(List<Transition> transitions) {
		this.transitions = transitions;
	}
	public String getNotation() {
		return notation;
	}
	public void setNotation(String notation) {
		this.notation = notation;
	}

	public double getPro() {
		return pro;
	}

	public void setPro(double pro) {
		this.pro = pro;
	}

	public boolean isCombine() {
		return isCombine;
	}

	public void setCombine(boolean isCombine) {
		this.isCombine = isCombine;
	}

	public String getTmcType() {
		return tmcType;
	}

	public void setTmcType(String tmcType) {
		this.tmcType = tmcType;
	}

	public String getNames() {
		return names;
	}

	public void setNames(String names) {
		this.names = names;
	}

	public String getOwned() {
		return owned;
	}

	public void setOwned(String owned) {
		this.owned = owned;
	}
	
}
