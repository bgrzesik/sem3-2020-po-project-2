package project2.entity;

public interface EntityVisitor {

    void visitPlayer(PlayerEntity player);

    void visitEnemy(EnemyEntity enemy);

    void visitCherry(CherryEntity cherry);

    void visitMap(MapEntity map);

}
