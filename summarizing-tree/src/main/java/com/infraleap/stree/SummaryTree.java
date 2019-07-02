package com.infraleap.stree;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.function.ValueProvider;

import java.util.*;
import java.util.function.BinaryOperator;

public class SummaryTree extends VerticalLayout {

	@SuppressWarnings("WeakerAccess")
	public static abstract class Node<T>{
		private final String name;
		Node(String name){
			this.name = name;
		}

		final String getName(){
			return this.name;
		}

		abstract T getValue(int which);
		abstract int getNumberOfValues();
		abstract Collection<Node<T>> getChildren();
	}

	public static class SummaryNode<T> extends Node<T>{

		private final ArrayList<Node<T>> children = new ArrayList<>();

		private final T neutral;
		private final BinaryOperator<T> summarizer;

		@SafeVarargs
		public SummaryNode(String name, T neutral, BinaryOperator<T> summarizer, Node<T>... children) {
			super(name);

			this.neutral = neutral;
			this.summarizer = summarizer;

			this.children.addAll(Arrays.asList(children));
		}

		public T getValue(int which){
			return children.stream().map(child->child.getValue(which)).reduce(neutral, summarizer);
		}

		public Collection<Node<T>> getChildren(){
			return Collections.unmodifiableList(children);
		}

		@Override
		int getNumberOfValues() {
			if (children.isEmpty()){
				throw new RuntimeException("No children yet, don't know about their number of values.");
			}
			else{
				return children.get(0).getNumberOfValues();
			}
		}
	}

	public static class Leaf<T> extends Node<T>{

		private final List<T> values;

		public Leaf(String name, List<T> values) {
			super(name);
			this.values = Collections.unmodifiableList(values);
		}

		@Override
		T getValue(int which) {
			return values.get(which);
		}

		@Override
		int getNumberOfValues() {
			return values.size();
		}

		@Override
		public Collection<Node<T>> getChildren(){
			return Collections.emptyList();
		}
	}


	public <T> SummaryTree (Node<T> root){
		TreeGrid<Node<T>> treeGrid = new TreeGrid<>();
		treeGrid.setSizeFull();

		ValueProvider<Node<T>, String> vp = Node::getName;
		Grid.Column<Node<T>> column = treeGrid.addHierarchyColumn(vp);
		column.setHeader("Name");
		//column.setAutoWidth(true);

		for (int i = 0; i < root.getNumberOfValues(); i++) {
			final int j=i;
			ValueProvider<Node<T>, T> valueProvider = n->n.getValue(j);

			Grid.Column<Node<T>> col = treeGrid.addColumn(valueProvider);
			col.setHeader("Value "+j);
			//col.setAutoWidth(true);
		}

		treeGrid.setItems(Collections.singleton(root), Node::getChildren);
		treeGrid.expandRecursively(Collections.singleton(root), Integer.MAX_VALUE);
		//treeGrid.recalculateColumnWidths();

		//treeGrid.setItemDetailsRenderer(new ComponentRenderer<>(node -> new Icon(node.getChildren().isEmpty()? VaadinIcon.CIRCLE : VaadinIcon.ABACUS)));
		treeGrid.setItemDetailsRenderer(new ComponentRenderer<>(() -> new Icon(VaadinIcon.ABACUS)));
		treeGrid.setDetailsVisible(root, true);

		Button button = new Button("setDetailsVisible(root, true) and setDetailsVisibleOnClick(true)");
		button.addClickListener( evt -> {
			treeGrid.setDetailsVisible(root, true);
			treeGrid.setDetailsVisibleOnClick(true);
		});

		add(treeGrid, button);

	}

}
