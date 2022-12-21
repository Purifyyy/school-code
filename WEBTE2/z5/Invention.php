<?php

require_once "MyPDO.php";

class Invention
{
    /* @var MyPDO */
    protected $db;

    protected int $id;
    protected int $inventor_id;

    public function __construct()
    {
        $this->db = MyPDO::instance();
    }

    /**
     * @return int
     */
    public function getId(): int
    {
        return $this->id;
    }

    /**
     * @return int
     */
    public function getInventorId(): int
    {
        return $this->inventor_id;
    }

    /**
     * @param int $inventor_id
     */
    public function setInventorId(int $inventor_id): void
    {
        $this->inventor_id = $inventor_id;
    }

    /**
     * @return DateTime
     */
    public function getInventionDate(): DateTime
    {
        return $this->invention_date;
    }

    /**
     * @param string $invention_date
     */
    public function setInventionDate(string $invention_date): void
    {
        $this->invention_date = DateTime::createFromFormat('d.m.Y', $invention_date);
    }

    /**
     * @return string
     */
    public function getDescription(): string
    {
        return $this->description;
    }

    /**
     * @param string $description
     */
    public function setDescription(string $description): void
    {
        $this->description = $description;
    }
    protected DateTime $invention_date;
    protected string $description;

    public static function findByCentury($century): bool|array
    {
        return MyPDO::instance()->run("SELECT invention_date, description FROM inventions WHERE FLOOR((year(invention_date) + 99) / 100) = ?", [$century])->fetchAll();
    }

    public static function findByYear($year): bool|array
    {
        return MyPDO::instance()->run("SELECT invention_date, description FROM inventions WHERE year(invention_date) = ?;", [$year])->fetchAll();
    }

    public function save()
    {
        $this->db->run("INSERT into inventions
            (`inventor_id`, `invention_date`, `description`) values (?, ?, ?)",
            [$this->inventor_id, isset($this->invention_date) ? $this->invention_date->format('Y.m.d') : null, $this->description]);
        $this->id = $this->db->lastInsertId();
    }

    public function toArray()
    {
        return ["id" => $this->id, "inventor_id"=>$this->inventor_id, "description" => $this->description, "invention_date" => $this->invention_date->format('Y-m-d')];
    }
}