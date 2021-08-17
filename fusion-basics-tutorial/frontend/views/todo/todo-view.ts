import {
  LitElement,
  html,
  css,
  customElement,
  internalProperty,
} from "lit-element";

import "@vaadin/vaadin-text-field";
import "@vaadin/vaadin-button";
import "@vaadin/vaadin-checkbox";
import { Binder, field } from "@vaadin/form";
import { getTodos, saveTodo } from "../../generated/TodoEndpoint";
import Todo from "../../generated/com/example/application/db/Todo";
import TodoModel from "../../generated/com/example/application/db/TodoModel";

@customElement("todo-view")
export class TodoView extends LitElement {
  @internalProperty()
  private todos: Todo[] = [];
  private binder = new Binder(this, TodoModel);

  static styles = css`
    :host {
      display: block;
      padding: var(--lumo-space-m) var(--lumo-space-l);
    }
  `;

  render() {
    return html`
      <div class="form">
        <vaadin-text-field
          ...=${field(this.binder.model.task)}
        ></vaadin-text-field>
        <vaadin-button
          theme="primary"
          @click=${this.addTask}
          ?disabled=${this.binder.invalid}
          >Add</vaadin-button
        >
      </div>
      <div class="todos">
        ${this.todos.map(
          (todo) => html`
            <div class="todo">
              <vaadin-checkbox
                ?checked=${todo.done}
                @checked-changed=${(e: CustomEvent) =>
                  this.updateTodoState(todo, e.detail.value)}
              ></vaadin-checkbox>
              <span>${todo.task}</span>
            </div>
          `
        )}
      </div>
    `;
  }

  async connectedCallback() {
    super.connectedCallback();
    this.todos = await getTodos();
  }

  async addTask() {
    const createdTodo = await this.binder.submitTo(saveTodo);
    if (createdTodo) {
      this.todos = [...this.todos, createdTodo];
      this.binder.clear();
    }
  }

  updateTodoState(todo: Todo, done: boolean) {
    const updatedTodo = { ...todo, done };
    this.todos = this.todos.map((t) => (t.id === todo.id ? updatedTodo : t));
    saveTodo(updatedTodo);
  }
}
