package com.infraleap;

import com.infraleap.stree.SummaryTree;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.function.BinaryOperator;

/**
 * The main view contains a button and a click listener.
 */
@Route("")
@PWA(name = "Project Base for Vaadin Flow", shortName = "Project Base")
@SuppressWarnings("unused")
public class MainView extends VerticalLayout {

	@SuppressWarnings("unused")
	public MainView() {

		BigDecimal neutral = BigDecimal.ZERO;
		BinaryOperator<BigDecimal> sum = BigDecimal::add;

		SummaryTree.Leaf<BigDecimal> node1 = new SummaryTree.Leaf<>("Eins", Arrays.asList(new BigDecimal(1), new BigDecimal(2)));

		SummaryTree.Leaf<BigDecimal> node21 = new SummaryTree.Leaf<>("2.1", Arrays.asList(new BigDecimal(10), new BigDecimal(50)));
		SummaryTree.Leaf<BigDecimal> node221 = new SummaryTree.Leaf<>("2.2.1", Arrays.asList(new BigDecimal(20), new BigDecimal(60)));
		SummaryTree.Leaf<BigDecimal> node222 = new SummaryTree.Leaf<>("2.2.2", Arrays.asList(new BigDecimal(20), new BigDecimal(60)));
		SummaryTree.SummaryNode<BigDecimal> node22 = new SummaryTree.SummaryNode<>("2.2 (multiplied)", BigDecimal.ONE, BigDecimal::multiply, node221, node222);
		SummaryTree.Leaf<BigDecimal> node23 = new SummaryTree.Leaf<>("2.3", Arrays.asList(new BigDecimal(30), new BigDecimal(70)));
		SummaryTree.SummaryNode<BigDecimal> node2 = new SummaryTree.SummaryNode<>("Zwei", neutral, sum, node21, node22, node23);

		SummaryTree.Leaf node3 = new SummaryTree.Leaf<>("Drei", Arrays.asList(new BigDecimal(100), new BigDecimal(200)));

		SummaryTree.SummaryNode<BigDecimal> summaryNode = new SummaryTree.SummaryNode<BigDecimal>("Long long long long loooooooooong name", neutral, sum, node1, node2, node3);

		SummaryTree summaryTree = new SummaryTree(summaryNode);
		summaryTree.setSizeFull();

		add(summaryTree);

		setSizeFull();
	}

}
