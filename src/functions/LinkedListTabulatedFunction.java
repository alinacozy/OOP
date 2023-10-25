package functions;

public class LinkedListTabulatedFunction implements TabulatedFunction, java.io.Serializable {
    private class FunctionNode {
        public FunctionPoint data;
        public FunctionNode prev;
        public FunctionNode next;

        public FunctionNode() {
            data = null;
            prev = null;
            next = null;
        }

        public FunctionNode(FunctionPoint point) {
            data = point;
            prev = null;
            next = null;
        }
    }

    private FunctionNode head;
    private int pCount;
    private int currentIndex;
    private FunctionNode currentNode;

    private FunctionNode getNodeByIndex(int index){
        index=index% pCount; // так как список циклический, нам могут передать индекс, больший размера
        if (Math.abs(index- currentIndex)>index){ //в этом блоке ведем отсчет от головы вперед
            currentNode =head.next;
            currentIndex =index;
            int steps=index; //количество шагов/элементов, которые нам нужно пройти
            while(steps-- >0){
                currentNode = currentNode.next;
            }
        } else if (Math.abs(index- currentIndex)> pCount -index){ // в этом блоке отсчет от головы назад
            currentNode =head.next;
            currentIndex =index;
            int steps= pCount -index; //количество шагов/элементов, которые нам нужно пройти
            while(steps-->0){
                currentNode = currentNode.prev;
            }
        } else if (index > currentIndex) { //отсчет от текущего элемента вперед
            int steps=index- currentIndex;
            currentIndex =index;
            while(steps-->0){
                currentNode = currentNode.next;
            }
        } else { //отсчет от текущего элемента назад
            int steps= currentIndex -index;
            currentIndex =index;
            while (steps-->0){
                currentNode = currentNode.prev;
            }
        }
        return currentNode;
    }

    private FunctionNode addNodeToTail(){
        FunctionNode newNode=new FunctionNode();
        if (head.next==head){ //если список до этого был пустым
            head.next=newNode;
            newNode.prev=newNode.next=newNode;
        }
        else {
            newNode.prev = head.next.prev;
            newNode.next = head.next;
            head.next.prev.next = newNode;
            head.next.prev = newNode;
        }
        pCount++;
        return newNode;
    }

    private FunctionNode addNodeByIndex(int index){
        index=index%pCount;
        FunctionNode placeOfInsert=getNodeByIndex(index); //находим узел списка, перед которым будем вставлять
        FunctionNode newNode=new FunctionNode(); //создаем новый узел
        newNode.prev=placeOfInsert.prev;
        newNode.next=placeOfInsert;
        placeOfInsert.prev.next=newNode;
        placeOfInsert.prev=newNode;
        pCount++;
        currentNode =newNode;
        currentIndex =index;
        return newNode;
    }

    private FunctionNode deleteNodeByIndex(int index){
        FunctionNode nodeToDelete=getNodeByIndex(index); //находим узел, который будем удалять
        nodeToDelete.prev.next=nodeToDelete.next;
        nodeToDelete.next.prev=nodeToDelete.prev;
        pCount--;
        return nodeToDelete;
    }

    public LinkedListTabulatedFunction(double leftX, double rightX, int pointsCount) throws IllegalArgumentException{
        if (leftX>=rightX){
            throw new IllegalArgumentException("The left border of the area must be lower than the right");
        }
        if (pointsCount<2){
            throw new IllegalArgumentException("The number of points must be more than two");
        }
        //pCount=pointsCount;
        double interval=(rightX-leftX)/(pointsCount-1); //вычисляем интервал между точками
        head=new FunctionNode();
        head.next=head.prev=head; //создали пустой список
        double currentX=leftX;
        for (int i=0; currentX<=rightX; currentX+=interval, i++) { //добавляем элементы в список
            FunctionNode newNode=addNodeToTail();
            newNode.data = new FunctionPoint(currentX, 0);
        }
        currentNode=head.next;
    }

    public LinkedListTabulatedFunction(double leftX, double rightX, double[] values) throws IllegalArgumentException {
        if (leftX>=rightX){
            throw new IllegalArgumentException("The left border of the area must be lower than the right");
        }
        if (values.length<2){
            throw new IllegalArgumentException("The number of points must be more than two");
        }
        head=new FunctionNode();
        head.next=head.prev=head; //создали пустой список
        double interval=(rightX-leftX)/(values.length-1);
        double currentX=leftX;
        for (int i=0; currentX<=rightX; currentX+=interval, i+=1) {
            FunctionNode newNode=addNodeToTail(); //добавляем элементы по одному в конец списка
            newNode.data = new FunctionPoint(currentX, values[i]);
        }
        currentNode=head.next;
    }

    public LinkedListTabulatedFunction(FunctionPoint[] points) throws IllegalArgumentException {
        if (points.length<2){
            throw new IllegalArgumentException("The number of points must be more than two");
        }
        head=new FunctionNode();
        head.next=head.prev=head; //создали пустой список
        for (int i=0; i<points.length; i++) { // пробегаемся по массиву и добавляем элементы по одному в список
            if (i!=0 && points[i-1].getX()>=points[i].getX()){
                throw new IllegalArgumentException("The points must be ordered by abscissa");
            }
            FunctionNode newNode=addNodeToTail(); //добавляем элементы по одному в конец списка
            newNode.data = points[i];
        }
        currentNode=head.next;
    }

    public double getLeftDomainBorder(){
        return head.next.data.getX();
    }

    public double getRightDomainBorder(){
        return head.next.prev.data.getX();
    }

    public double getFunctionValue(double x){
        if (getLeftDomainBorder()<=x && x<=getRightDomainBorder()){ //если точка лежит в области определения
            if (x<currentNode.data.getX()) {  //если значение лежит до currentNode, считаем от head (иначе от currentNode)
                currentNode=head.next;
                currentIndex=0;
            }
            do{
                if (currentNode.data.getX()>=x){ //если нашли точку, которая по x больше нашего икса
                    return ( (currentNode.data.getY()-currentNode.prev.data.getY())*(x-currentNode.prev.data.getX())/(currentNode.data.getX()-currentNode.prev.data.getX()) + currentNode.prev.data.getY() );
                }
                currentNode=currentNode.next;
                currentIndex=(currentIndex+1)%pCount;
            } while (currentNode!=head.next);
        }
        return Double.NaN;
    }

    public int getPointsCount(){
        return pCount;
    }

    public FunctionPoint getPoint(int index) throws FunctionPointIndexOutOfBoundsException{
        if (index<0 || index>=pCount) {
            throw new FunctionPointIndexOutOfBoundsException();
        }
        return getNodeByIndex(index).data;
    }

    public void setPoint(int index, FunctionPoint point) throws FunctionPointIndexOutOfBoundsException, InappropriateFunctionPointException {
        if (index<0 || index>=pCount) {
            throw new FunctionPointIndexOutOfBoundsException();
        }

        if ((index!=0 && getNodeByIndex(index-1).data.getX()>=point.getX()) || (index!=pCount-1 && point.getX()>=getNodeByIndex(index+1).data.getX())){
            throw new InappropriateFunctionPointException("The abscissa of the point lies outside the interval");
        }
        getNodeByIndex(index).data=point;
    }

    public double getPointX(int index) throws FunctionPointIndexOutOfBoundsException{
        if (index<0 || index>=pCount) {
            throw new FunctionPointIndexOutOfBoundsException();
        }
        return getNodeByIndex(index).data.getX();
    }

    public void setPointX(int index, double x) throws FunctionPointIndexOutOfBoundsException, InappropriateFunctionPointException {
        if (index<0 || index>=pCount) {
            throw new FunctionPointIndexOutOfBoundsException();
        }
        if ((index!=0 && getNodeByIndex(index).data.getX()>=x) || (index!=pCount-1 && x>=getNodeByIndex(index).data.getX())){
            throw new InappropriateFunctionPointException("The abscissa of the point lies outside the interval");
        }
        getNodeByIndex(index).data.setX(x);
    }

    public double getPointY(int index) throws FunctionPointIndexOutOfBoundsException{
        if (index<0 || index>=pCount) {
            throw new FunctionPointIndexOutOfBoundsException();
        }
        return getNodeByIndex(index).data.getY();
    }

    public void setPointY(int index, double y) throws FunctionPointIndexOutOfBoundsException{
        if (index<0 || index>=pCount) {
            throw new FunctionPointIndexOutOfBoundsException();
        }
        getNodeByIndex(index).data.setY(y);
    }

    public void deletePoint(int index) throws FunctionPointIndexOutOfBoundsException{
        if (index<0 || index>=pCount) {
            throw new FunctionPointIndexOutOfBoundsException();
        }
        if (pCount<3){
            throw new IllegalStateException("Unable to delete a point because there are too few of them");
        }
        deleteNodeByIndex(index);
    }

    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException {
        if (point.getX()<currentNode.data.getX()) {  //если абсцисса новой точки лежит до currentNode, считаем от head (иначе от currentNode)
            currentNode=head.next;
            currentIndex=0;
        }
        do {
            if (currentNode.data.getX()>=point.getX()){ //если нашли точку, которая по x больше нашего икса, вставляем на место перед ней
                //вставляем новую точку до currentNode
                if (currentNode.data.getX()==point.getX()){
                    throw new InappropriateFunctionPointException("A point with such an abscissa already exists");
                }
                FunctionNode newNode=addNodeByIndex(currentIndex);
                newNode.data=point;
                return;
            }
            currentNode=currentNode.next;
            currentIndex=(currentIndex+1)%pCount;
        } while (currentNode!=head.next);
        //если мы вышли из цикла, значит наша новая точка больше всех остальных точек -> вставляем в хвост
        FunctionNode newNode=addNodeToTail();
        newNode.data=point;
    }
}
