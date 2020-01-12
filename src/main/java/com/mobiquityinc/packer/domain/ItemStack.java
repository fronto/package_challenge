package com.mobiquityinc.packer.domain;

import com.mobiquityinc.packer.util.SnapshotStack;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class ItemStack implements SnapshotStack<Item, ItemStack> {

    public Double combinedWeight() {
        return head.cumulativeWeight();
    }

    public Integer combinedCost() {
        return head.cumulativeCost();
    }

    public List<Integer> indexNumbers() {
        List<Integer> result = new LinkedList<>();
        CachingNode node = head;
        while (node != null)  {
            result.add(node.value.getIndexNumber());
            node = node.next;
        }
        result.sort(Comparator.naturalOrder());
        return result;
    }

    private CachingNode head;

    public void push(Item item) {
        if(head == null) {
            head = new CachingNode();
            head.value = item;
        } else {
            CachingNode temp = head;
            head = new CachingNode();
            head.next = temp;
            head.value = item;
        }

    }

    public Item pop() {
        if(head == null) {
            throw new IllegalStateException("cannot pop from empty stack");
        }
        Item temp = head.value;
        head = head.next;
        return temp;
    }

    public ItemStack snapshot() {
        ItemStack itemStack = new ItemStack();
        itemStack.head = this.head;
        return itemStack;
    }

    private static class CachingNode {

        Item value;
        CachingNode next;

        Optional<Double> cachedWeight = Optional.empty();
        Optional<Integer> cachedCost = Optional.empty();

        Double cumulativeWeight() {
            if(!cachedWeight.isPresent()) {
                if(next == null) {
                    cachedWeight = Optional.of(value.getWeight());
                } else {
                    cachedWeight = Optional.of(value.getWeight() + next.cumulativeWeight());
                }
            }
            return cachedWeight.get();
        }

        Integer cumulativeCost() {
            if(!cachedCost.isPresent()) {
                if(next == null) {
                    cachedCost = Optional.of(value.getCost());
                } else {
                    cachedCost = Optional.of(value.getCost() + next.cumulativeCost());
                }
            }
            return cachedCost.get();
        }

    }

}
